/*****************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 ****************************************************************/

package org.apache.cayenne;

import org.apache.cayenne.reflect.PropertyUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Implementation of DataObject that uses {@link Map} to store object fields.
 * This implementation was pre 4.1 default.
 */
public class CayenneDataObject extends BaseDataObject {

	private static final long serialVersionUID = -313743913882350400L;

	protected Map<String, Object> values = new HashMap<>();

	@Override
	public void setPersistenceState(int persistenceState) {
		super.setPersistenceState(persistenceState);
		// additionally clear values for HOLLOW state
		if (persistenceState == PersistenceState.HOLLOW) {
			values.clear();
		}
	}

	@Override
	Object readSimpleProperty(String property) {

		// side effect - resolves HOLLOW object
		Object object = readProperty(property);

		// if a null value is returned, there is still a chance to
		// find a non-persistent property via reflection
		if (object == null && !values.containsKey(property)) {
			object = PropertyUtils.getProperty(this, property);
		}

		return object;
	}

	@Override
	public Object readPropertyDirectly(String propName) {
		return values.get(propName);
	}

	@Override
	public void writePropertyDirectly(String propName, Object val) {
		values.put(propName, val);
	}

	@Override
	protected void appendProperties(StringBuffer buffer) {
		buffer.append("[");
		Iterator<Map.Entry<String, Object>> it = values.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();

			buffer.append(entry.getKey()).append("=>");
			Object value = entry.getValue();

			if (value instanceof Persistent) {
				buffer.append('{').append(((Persistent) value).getObjectId()).append('}');
			} else if (value instanceof Collection) {
				buffer.append("(..)");
			} else if (value instanceof Fault) {
				buffer.append('?');
			} else {
				buffer.append(value);
			}

			if (it.hasNext()) {
				buffer.append("; ");
			}
		}

		buffer.append("]");
	}

	@Override
	protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
		super.readState(in);
		values = (Map<String, Object>) in.readObject();
	}

	@Override
	protected void writeState(ObjectOutputStream out) throws IOException {
		super.writeState(out);
		out.writeObject(values);
	}

	/**
	 * Convenience method to invoke {@link Cayenne#makePath(String...)} from
	 * within a DataObject subclass to create a dotted path using the generated
	 * string constants for attributes and relationships.
	 *
	 * @deprecated since 4.1, use {@link Cayenne#makePath(String...)} instead
	 * @see Cayenne#makePath(String...)
	 * @since 3.1
	 */
	@Deprecated
	public static String makePath(String... pathParts) {
		return Cayenne.makePath(pathParts);
	}
}
