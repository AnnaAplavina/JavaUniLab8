package data;

import collectionitems.MusicBand;
import collectionitems.MusicGenre;
import collectionitems.WrongArgumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Class to manage the collection
 */
public class CollectionManager {
    private final Date initializationDate;
    private Vector<MusicBand> collection;
    private final DataManager dataManager;
    private final Set<Integer> IDs;

    public CollectionManager(DataManager dataManager) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        initializationDate = new Date();
        formatter.format(initializationDate);
        this.dataManager = dataManager;
        collection = dataManager.getCollection();
        sortCollectionBySize();
        IDs = new HashSet<>();
        collection.forEach(band -> IDs.add(band.getId()));
    }

    /**
     * @return time when this collection was loaded
     */
    public Date getInitializationDate(){
        return initializationDate;
    }

    /**
     *
     * @return size of the collection
     */
    public int getCollectionSize(){
        return collection.size();
    }

    /**
     *
     * @return all collection elements string representation
     */
    public String toString(){
        StringBuilder res = new StringBuilder();
        MusicBand[] bands = new MusicBand[collection.size()];
        collection.toArray(bands);
        List<MusicBand> bandsList = new ArrayList<>(Arrays.asList(bands));
        bandsList.forEach(band -> {res.append(band); res.append("\n\n");});
        return res.toString();
    }

    /**
     * reads new band from user
     */
    public void addNewElementFromUser(MusicBand band){
        int newId = generateNewId();
        band.setId(newId);
        IDs.add(newId);
        collection.add(band);
        sortCollectionBySize();
    }

    /**
     * add new element from user to a particular place in the collection
     * @param index index where new element is supposed to be
     * @throws ArrayIndexOutOfBoundsException given index is out of bounds
     */
    public void addNewElementFromUser(int index, MusicBand band) throws ArrayIndexOutOfBoundsException
    {
        if(index >= collection.size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        int newId = generateNewId();
        band.setId(newId);
        IDs.add(newId);
        collection.add(index, band);
        sortCollectionBySize();
    }

    /**
     *
     * @param id id of an element
     * @return element of the collection with a given id
     */
    public MusicBand findElementById(int id){
        MusicBand band = null;
        List<MusicBand> foundBands = collection.stream().filter(b -> b.getId() == id).collect(Collectors.toList());
        if(foundBands.size() > 0){
            band = foundBands.get(0);
        }
        return band;
    }

    /**
     * remofe element with a particular id from the collection
     * @param id if of the band
     * @throws WrongArgumentException id was incorrect(element with such id does not exist)
     */
    public void removeElementById(int id) throws WrongArgumentException{
        MusicBand band = findElementById(id);
        if(band == null){
            throw new WrongArgumentException("no element with such id");
        }
        collection.removeElement(band);
        sortCollectionBySize();
    }

    /**
     * change element with a particular id
     * @param id id of the element
     * @throws WrongArgumentException id was incorrect(element with such id does not exist)
     */
    public void changeElementFromUser(int id, MusicBand band) throws WrongArgumentException{
        MusicBand oldBand = findElementById(id);
        if(oldBand == null){
            throw new WrongArgumentException("no element with such id");
        }
        collection.removeElement(band);
        band.setId(id);
        collection.add(band);
        sortCollectionBySize();
    }

    /**
     * clears the collection
     */
    public void clearCollection(){
        collection = new Vector<>();
    }

    /**
     * save the collection to the file
     * @throws IOException could not write collection to file
     */
    public void saveCollection() throws IOException {
        dataManager.saveCollection(collection);
        System.out.println("Saving collection");
    }

    /**
     * get max band in collection
     * @return max band
     * @throws EmptyCollectionException collection was empty
     */
    public MusicBand getMax() throws EmptyCollectionException {
        if(collection.size() == 0){
            throw new EmptyCollectionException();
        }
        return collection.stream().max(MusicBand::compareTo).get();
    }

    /**
     * get min band in collection
     * @return min band
     * @throws EmptyCollectionException collection was empty
     */
    public MusicBand getMin() throws EmptyCollectionException {
        if(collection.size() == 0){
            throw new EmptyCollectionException();
        }
        return collection.stream().min(MusicBand::compareTo).get();
    }

    /**
     * add new element to collection if it is the max element
     * @return true if successfully added, otherwise false
     */
    public boolean addIfMax(MusicBand newBand){
        try {
            MusicBand maxBand;
            maxBand = getMax();
            if(newBand.compareTo(maxBand) > 0){
                newBand.setId(generateNewId());
                collection.add(newBand);
                sortCollectionBySize();
                return true;
            }
        } catch (EmptyCollectionException e) {
            newBand.setId(generateNewId());
            collection.add(newBand);
            return true;
        }
        return false;
    }

    /**
     * add new element to collection if it is the min element
     * @return true if successfully added, otherwise false
     */
    public boolean addIfMin(MusicBand newBand){
        try {
            MusicBand minBand;
            minBand = getMin();
            if(newBand.compareTo(minBand) < 0){
                newBand.setId(generateNewId());
                collection.add(newBand);
                sortCollectionBySize();
                return true;
            }
        } catch (EmptyCollectionException e) {
            newBand.setId(generateNewId());
            collection.add(newBand);
            return true;
        }
        return false;
    }

    /**
     * count bands with genre lesser than given genre, bands with no genre are not counted
     * @param genre genre to compare other genres to
     * @return the amount of counted bands
     */
    public int countWithLesserGenre(MusicGenre genre){
        return (int) collection.stream().filter(b -> b.getGenre() != null && genre.compareTo(b.getGenre()) > 0).count();
    }

    /**
     * find all bands with description that starts with a given string
     * @param start start of description
     * @return list of all found bands
     */
    public List<MusicBand> getWithDescriptionStart(String start){
        return collection.stream().filter(b -> b.getDescription() != null
                && b.getDescription().startsWith(start)).collect(Collectors.toList());
    }

    /**
     * get list of bands sorted in descending order
     * @return list of bands
     */
    public List<MusicBand> getDescending(){
        List<MusicBand> res = new ArrayList<>(collection);
        res.sort(MusicBand::compareTo);
        return res;
    }

    private int generateNewId(){
        return IDs.stream().max(Integer::compareTo).isPresent() ? IDs.stream().max(Integer::compareTo).get()+1 : 1;
    }

    private void sortCollectionBySize(){
        collection.sort((o1, o2) -> {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] arr1 = {};
            byte[] arr2 = {};
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
                outputStream.writeObject(o1);
                outputStream.flush();
                arr1 = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.reset();
                outputStream.writeObject(o2);
                arr2 = byteArrayOutputStream.toByteArray();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return arr1.length - arr2.length;
        });
    }
}
