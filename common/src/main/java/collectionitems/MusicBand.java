package collectionitems;

import xml.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class for Music Band
 */
@XmlRootElement
public class MusicBand implements Serializable, Comparable<MusicBand> {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int numberOfParticipants; //Значение поля должно быть больше 0
    private long albumsCount; //Значение поля должно быть больше 0
    private String description; //Поле может быть null
    private MusicGenre genre; //Поле может быть null
    private Album bestAlbum; //Поле может быть null

    private String ownerUsername;

    public MusicBand(){
        this.id = 1;
        name="Default";
        coordinates = new Coordinates();
        creationDate = LocalDateTime.now();
        numberOfParticipants = 1;
        albumsCount = 1;
        description = null;
        genre = null;
        bestAlbum = null;
    }

    public void setOwnerUsername(String owner){
        this.ownerUsername = owner;
    }

    public String getOwnerUsername(){
        return ownerUsername;
    }

    @XmlAttribute
    public void setName(String name) throws WrongArgumentException {
        if(name == null || name.equals("")){
            throw new WrongArgumentException("band name can't be null or empty");
        }
        this.name = name;
    }

    @XmlElement(name="coordinates")
    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @XmlAttribute
    public void setNumberOfParticipants(int numberOfParticipants) throws WrongArgumentException {
        if(numberOfParticipants <= 0){
            throw new WrongArgumentException("number of participant must be greater than 0");
        }
        this.numberOfParticipants = numberOfParticipants;
    }

    @XmlAttribute
    public void setAlbumsCount(long albumsCount) throws WrongArgumentException {
        if(albumsCount <= 0){
            throw new WrongArgumentException("albums count must be greater than 0");
        }
        this.albumsCount = albumsCount;
    }

    @XmlAttribute
    public void setDescription(String description){
        this.description = description;
    }

    @XmlAttribute
    public void setGenre(MusicGenre genre){
        this.genre = genre;
    }

    @XmlElement(name="bestAlbum")
    public void setBestAlbum(Album bestAlbum){
        this.bestAlbum = bestAlbum;
    }

    @XmlAttribute
    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public MusicGenre getGenre(){
        return genre;
    }

    public String getDescription(){
        return description;
    }

    public int getNumberOfParticipants(){
        return numberOfParticipants;
    }

    public String getName(){
        return name;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public long getAlbumsCount(){
        return albumsCount;
    }

    public Album getBestAlbum(){
        return bestAlbum;
    }

    public LocalDateTime getCreationDate(){
        return creationDate;
    }


    public String toString(){
        return "Id: " + id + "\n" + "Name: " + name + "\n" + "collectionitems.Coordinates: " + coordinates
                + "\nCreation date: " + creationDate + "\nNumber of participants: " + numberOfParticipants
                + "\nAlbums count: " + albumsCount + "\nDescription: " + description + "\nGenre: " + genre
                + "\nBest album: " + bestAlbum;
    }

    @Override
    public int compareTo(MusicBand band) {
        if(band == null){
            return 1;
        }
        if(albumsCount - band.albumsCount > 0){
            return 1;
        }
        if(albumsCount - band.albumsCount == 0){
            return 0;
        }
        return -1;
    }

    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject == this){
            return true;
        }
        if(!(anotherObject instanceof MusicBand)){
            return false;
        }
        MusicBand otherBand = (MusicBand) anotherObject;
        boolean equalCoordinates = coordinates.equals(otherBand.coordinates);
        boolean equalName = name.equals(otherBand.name);
        boolean equalNumberOfParticipants = numberOfParticipants == otherBand.getNumberOfParticipants();
        boolean equalAlbumsCount = albumsCount == otherBand.getAlbumsCount();
        boolean equalDescription = false;
        if(description == null && otherBand.getDescription() == null){
            equalDescription = true;
        }
        else if(description != null && description.equals(otherBand.getDescription())){
            equalDescription = true;
        }
        boolean equalGenre = false;
        if(genre == null && otherBand.getGenre() == null){
            equalGenre = true;
        }
        else if(genre != null && genre.equals(otherBand.getGenre())){
            equalGenre = true;
        }
        boolean equalBestAlbum = false;
        if(bestAlbum == null && otherBand.bestAlbum == null){
            equalBestAlbum = true;
        }
        else if(bestAlbum != null && bestAlbum.equals(otherBand.getBestAlbum())){
            equalBestAlbum = true;
        }
        return equalCoordinates && equalName && equalNumberOfParticipants && equalAlbumsCount &&
                equalDescription && equalGenre && equalBestAlbum;
    }
}
