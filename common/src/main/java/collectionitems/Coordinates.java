package collectionitems;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * collectionitems.Coordinates
 */
@XmlRootElement
public class Coordinates implements Serializable {
    private Float x; //Поле не может быть null
    private float y; //Максимальное значение поля: 323

    public Coordinates(){
        x = 0F;
    }

    @XmlAttribute
    public void setX(Float x) throws WrongArgumentException{
        if(x == null){
            throw new WrongArgumentException("x can't be null");
        }
        this.x = x;
    }

    @XmlAttribute
    public void setY(float y) throws WrongArgumentException {
        if(y > 323){
            throw new WrongArgumentException("y can't be greater than 323");
        }
        this.y = y;
    }

    public Float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public String toString(){
        return "x=" + x + " y=" + y;
    }

    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject == null){
            return false;
        }
        if(!(anotherObject instanceof Coordinates)){
            return false;
        }
        Coordinates otherCoordinates = (Coordinates) anotherObject;
        return Math.abs(x - otherCoordinates.x) < 0.001 && Math.abs(y - otherCoordinates.y) < 0.001;
    }
}
