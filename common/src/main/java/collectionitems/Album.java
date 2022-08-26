package collectionitems;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Class for album
 */
@XmlRootElement
public class Album implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long tracks; //Значение поля должно быть больше 0
    private Integer length; //Поле может быть null, Значение поля должно быть больше 0
    private Float sales; //Поле не может быть null, Значение поля должно быть больше 0

    public String getName() {
        return name;
    }

    public long getTracks() {
        return tracks;
    }

    public Integer getLength(){
        return length;
    }

    public Float getSales(){
        return sales;
    }

    public Album(){
        name="default";
        tracks = 1;
        length = null;
        sales = 1F;
    }

    @XmlAttribute
    public void setName(String name) throws WrongArgumentException {
        if(name == null){
            throw new WrongArgumentException("Album name can't be null");
        }
        this.name = name;
    }

    @XmlAttribute
    public void setTracks(long tracks) throws WrongArgumentException {
        if(tracks <= 0){
            throw new WrongArgumentException("tracks must be more than 0");
        }
        this.tracks = tracks;
    }

    @XmlAttribute
    public void setLength(Integer length) throws WrongArgumentException {
        if(!(length == null) && length<=0){
            throw new WrongArgumentException("length must be greater than 0");
        }
        this.length = length;
    }

    @XmlAttribute
    public void setSales(Float sales) throws WrongArgumentException {
        if(sales == null || sales <=0){
            throw new WrongArgumentException("sales can't be null and must be greater than 0");
        }
        this.sales = sales;
    }

    public String toString(){
        return name + " | " + tracks + " tracks" + " | " + length + " length" + " | " + sales + " sales";
    }

    @Override
    public boolean equals(Object otherObj){
        if(otherObj == null){
            return false;
        }
        if(!(otherObj instanceof Album)){
            return false;
        }
        Album otherAlbum = (Album) otherObj;
        boolean equalName = name.equals(otherAlbum.getName());
        boolean equalTracks = tracks == otherAlbum.getTracks();
        boolean equalLength = length.equals(otherAlbum.length);
        boolean equalSales = sales.equals(otherAlbum.getSales());
        return equalName && equalTracks && equalLength && equalSales;
    }
}
