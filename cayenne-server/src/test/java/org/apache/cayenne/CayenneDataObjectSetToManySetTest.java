package org.apache.cayenne;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.apache.cayenne.di.Inject;
import org.apache.cayenne.test.jdbc.DBHelper;
import org.apache.cayenne.test.jdbc.TableHelper;
import org.apache.cayenne.testdo.map_to_many.MapToMany;
import org.apache.cayenne.testdo.relationships_set_to_many.SetToMany;
import org.apache.cayenne.unit.di.server.CayenneProjects;
import org.apache.cayenne.unit.di.server.ServerCase;
import org.apache.cayenne.unit.di.server.UseServerRuntime;
import org.junit.Before;
import org.junit.Test;

@UseServerRuntime(CayenneProjects.RELATIONSHIPS_SET_TO_MANY_PROJECT)
public class CayenneDataObjectSetToManySetTest extends ServerCase {

	@Inject
	protected ObjectContext context;

	@Inject
	protected DBHelper dbHelper;

	protected TableHelper tSetToMany;
	protected TableHelper tSetToManyTarget;

	@Before
	public void setUp() throws Exception {
		tSetToMany = new TableHelper(dbHelper, "SET_TO_MANY");
		tSetToMany.setColumns("ID");

		tSetToManyTarget = new TableHelper(dbHelper, "SET_TO_MANY_TARGET");
		tSetToManyTarget.setColumns("ID", "SET_TO_MANY_ID");
	}

	protected void createTestDataSet() throws Exception {
		tSetToMany.insert(1);
		tSetToMany.insert(2);
		tSetToManyTarget.insert(1, 1);
		tSetToManyTarget.insert(2, 1);
		tSetToManyTarget.insert(3, 1);
		tSetToManyTarget.insert(4, 2);
	}

	/**
	 * Testing if collection type is set, everything should work fine without an
	 * runtimexception
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRelationCollectionTypeMap() throws Exception {
		createTestDataSet();

		SetToMany o1 = Cayenne.objectForPK(context, SetToMany.class, 1);
		assertTrue(o1.readProperty(SetToMany.TARGETS_PROPERTY) instanceof Set);
		boolean catchedSomething = false;
		try {
			o1.setToManyTarget(SetToMany.TARGETS_PROPERTY, new ArrayList<MapToMany>(0), true);
		} catch (RuntimeException e) {
			catchedSomething = true;
		}
		assertEquals(catchedSomething, false);
		assertEquals(o1.getTargets().size(), 0);
	}
}