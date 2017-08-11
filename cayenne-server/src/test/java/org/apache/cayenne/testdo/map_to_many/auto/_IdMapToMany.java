package org.apache.cayenne.testdo.map_to_many.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;
import org.apache.cayenne.testdo.map_to_many.IdMapToManyTarget;

/**
 * Class _IdMapToMany was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _IdMapToMany extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "ID";

    public static final Property<Map<Object, IdMapToManyTarget>> TARGETS = Property.create("targets", Map.class);


    protected Object targets;

    public void addToTargets(IdMapToManyTarget obj) {
        addToManyTarget("targets", obj, true);
    }

    public void removeFromTargets(IdMapToManyTarget obj) {
        removeToManyTarget("targets", obj, true);
    }

    @SuppressWarnings("unchecked")
    public Map<Object, IdMapToManyTarget> getTargets() {
        return (Map<Object, IdMapToManyTarget>)readProperty("targets");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "targets":
                return this.targets;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "targets":
                this.targets = val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.targets);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.targets = in.readObject();
    }

}
