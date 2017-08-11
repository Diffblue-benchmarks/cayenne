package org.apache.cayenne.testdo.relationships_clob.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;
import org.apache.cayenne.testdo.relationships_clob.ClobDetail;

/**
 * Class _ClobMaster was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _ClobMaster extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String CLOB_MASTER_ID_PK_COLUMN = "CLOB_MASTER_ID";

    public static final Property<String> CLOB_COLUMN = Property.create("clobColumn", String.class);
    public static final Property<String> NAME = Property.create("name", String.class);
    public static final Property<List<ClobDetail>> DETAILS = Property.create("details", List.class);

    protected String clobColumn;
    protected String name;

    protected Object details;

    public void setClobColumn(String clobColumn) {
        beforePropertyWrite("clobColumn", this.clobColumn, clobColumn);
        this.clobColumn = clobColumn;
    }

    public String getClobColumn() {
        beforePropertyRead("clobColumn");
        return this.clobColumn;
    }

    public void setName(String name) {
        beforePropertyWrite("name", this.name, name);
        this.name = name;
    }

    public String getName() {
        beforePropertyRead("name");
        return this.name;
    }

    public void addToDetails(ClobDetail obj) {
        addToManyTarget("details", obj, true);
    }

    public void removeFromDetails(ClobDetail obj) {
        removeToManyTarget("details", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<ClobDetail> getDetails() {
        return (List<ClobDetail>)readProperty("details");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "clobColumn":
                return this.clobColumn;
            case "name":
                return this.name;
            case "details":
                return this.details;
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
            case "clobColumn":
                this.clobColumn = (String)val;
                break;
            case "name":
                this.name = (String)val;
                break;
            case "details":
                this.details = val;
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
        out.writeObject(this.clobColumn);
        out.writeObject(this.name);
        out.writeObject(this.details);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.clobColumn = (String)in.readObject();
        this.name = (String)in.readObject();
        this.details = in.readObject();
    }

}
