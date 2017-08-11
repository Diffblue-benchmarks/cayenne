package org.apache.cayenne.testdo.testmap.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;
import org.apache.cayenne.testdo.testmap.Artist;

/**
 * Class _Painting1 was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Painting1 extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String PAINTING_ID_PK_COLUMN = "PAINTING_ID";

    public static final Property<BigDecimal> ESTIMATED_PRICE = Property.create("estimatedPrice", BigDecimal.class);
    public static final Property<String> PAINTING_TITLE = Property.create("paintingTitle", String.class);
    public static final Property<Artist> TO_ARTIST = Property.create("toArtist", Artist.class);

    protected BigDecimal estimatedPrice;
    protected String paintingTitle;

    protected Object toArtist;

    public void setEstimatedPrice(BigDecimal estimatedPrice) {
        beforePropertyWrite("estimatedPrice", this.estimatedPrice, estimatedPrice);
        this.estimatedPrice = estimatedPrice;
    }

    public BigDecimal getEstimatedPrice() {
        beforePropertyRead("estimatedPrice");
        return this.estimatedPrice;
    }

    public void setPaintingTitle(String paintingTitle) {
        beforePropertyWrite("paintingTitle", this.paintingTitle, paintingTitle);
        this.paintingTitle = paintingTitle;
    }

    public String getPaintingTitle() {
        beforePropertyRead("paintingTitle");
        return this.paintingTitle;
    }

    public void setToArtist(Artist toArtist) {
        setToOneTarget("toArtist", toArtist, true);
    }

    public Artist getToArtist() {
        return (Artist)readProperty("toArtist");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "estimatedPrice":
                return this.estimatedPrice;
            case "paintingTitle":
                return this.paintingTitle;
            case "toArtist":
                return this.toArtist;
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
            case "estimatedPrice":
                this.estimatedPrice = (BigDecimal)val;
                break;
            case "paintingTitle":
                this.paintingTitle = (String)val;
                break;
            case "toArtist":
                this.toArtist = val;
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
        out.writeObject(this.estimatedPrice);
        out.writeObject(this.paintingTitle);
        out.writeObject(this.toArtist);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.estimatedPrice = (BigDecimal)in.readObject();
        this.paintingTitle = (String)in.readObject();
        this.toArtist = in.readObject();
    }

}
