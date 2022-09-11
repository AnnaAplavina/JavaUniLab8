package data;

import collectionitems.MusicBand;
import collectionitems.MusicGenre;
import collectionitems.WrongArgumentException;
import data.database.bands.MusicBandDao;
import data.database.QueryExecutionException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Class to manage the collection
 */
public class CollectionManager {
    private final Date initializationDate;
    private Vector<MusicBand> collection;
    private final MusicBandDao musicBandDao;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public CollectionManager(MusicBandDao musicBandDao) throws QueryExecutionException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        initializationDate = new Date();
        formatter.format(initializationDate);
        this.musicBandDao = musicBandDao;
        collection = new Vector<>();
        collection.addAll(musicBandDao.getBandsFromDb());
    }

    /**
     * @return time when this collection was loaded
     */
    public Date getInitializationDate(){
        readWriteLock.readLock().lock();
        try{
            return initializationDate;
        }
        finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     *
     * @return size of the collection
     */
    public int getCollectionSize(){
        readWriteLock.readLock().lock();
        try{
            return collection.size();
        }
        finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     *
     * @return all collection elements string representation
     */
    public String toString(){
        readWriteLock.readLock().lock();
        try{
            StringBuilder res = new StringBuilder();
            MusicBand[] bands = new MusicBand[collection.size()];
            collection.toArray(bands);
            List<MusicBand> bandsList = new ArrayList<>(Arrays.asList(bands));
            bandsList.forEach(band -> {res.append(band); res.append("\n\n");});
            return res.toString();
        }
        finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * adds a new band to the database and collection
     */
    public void addNewElementFromUser(MusicBand band, String username) throws QueryExecutionException {
        readWriteLock.writeLock().lock();
        try{
            band.setId(musicBandDao.addBandToDb(band, username));
            band.setOwnerUsername(username);
            collection.add(band);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * add new element from user to a particular place in the collection
     * @param index index where new element is supposed to be
     * @throws ArrayIndexOutOfBoundsException given index is out of bounds
     */
    public void addNewElementFromUser(int index, MusicBand band, String username)
            throws ArrayIndexOutOfBoundsException, QueryExecutionException {
        readWriteLock.writeLock().lock();
        try{
            if(index >= collection.size()){
                throw new ArrayIndexOutOfBoundsException();
            }
            band.setId(musicBandDao.addBandToDb(band, username));
            band.setOwnerUsername(username);
            collection.add(index, band);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     *
     * @param id id of an element
     * @return element of the collection with a given id
     */
    public MusicBand findElementById(int id){
        readWriteLock.readLock().lock();
        try{
            MusicBand band = null;
            List<MusicBand> foundBands = collection.stream().filter(b -> b.getId() == id).collect(Collectors.toList());
            if(foundBands.size() > 0){
                band = foundBands.get(0);
            }
            return band;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * remofe element with a particular id from the collection
     * @param id if of the band
     * @throws WrongArgumentException id was incorrect(element with such id does not exist)
     */
    public void removeElementById(int id) throws WrongArgumentException, QueryExecutionException {
        readWriteLock.writeLock().lock();
        try{
            MusicBand band = findElementById(id);
            if(band == null){
                throw new WrongArgumentException("no element with such id");
            }
            musicBandDao.removeBandById(id);
            collection.removeElement(band);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * change element with a particular id
     * @param id id of the element
     * @throws WrongArgumentException id was incorrect(element with such id does not exist)
     */
    public void changeElementFromUser(int id, MusicBand band) throws WrongArgumentException, QueryExecutionException {
        readWriteLock.writeLock().lock();
        try{
            MusicBand oldBand = findElementById(id);
            if(oldBand == null){
                throw new WrongArgumentException("no element with such id");
            }
            musicBandDao.changeBandById(id, band);
            collection.removeElement(band);
            band.setId(id);
            collection.add(band);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * clears the collection
     */
    public void clearCollection(String username) throws QueryExecutionException {
        readWriteLock.writeLock().lock();
        try{
            musicBandDao.clearUserBands(username);
            collection.addAll(musicBandDao.getBandsFromDb());
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * get max band in collection
     * @return max band
     * @throws EmptyCollectionException collection was empty
     */
    public MusicBand getMax() throws EmptyCollectionException {
        readWriteLock.readLock().lock();
        try{
            if(collection.size() == 0){
                throw new EmptyCollectionException();
            }
            return collection.stream().max(MusicBand::compareTo).get();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * get min band in collection
     * @return min band
     * @throws EmptyCollectionException collection was empty
     */
    public MusicBand getMin() throws EmptyCollectionException {
        readWriteLock.readLock().lock();
        try{
            if(collection.size() == 0){
                throw new EmptyCollectionException();
            }
            return collection.stream().min(MusicBand::compareTo).get();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * add new element to collection if it is the max element
     * @return true if successfully added, otherwise false
     */
    public boolean addIfMax(MusicBand newBand, String username) throws QueryExecutionException {
        readWriteLock.writeLock().lock();
        try{
            try {
                MusicBand maxBand;
                maxBand = getMax();
                if(newBand.compareTo(maxBand) > 0){
                    newBand.setId(musicBandDao.addBandToDb(newBand, username));
                    collection.add(newBand);
                    return true;
                }
            } catch (EmptyCollectionException e) {
                newBand.setId(musicBandDao.addBandToDb(newBand, username));;
                collection.add(newBand);
                return true;
            }
            return false;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * add new element to collection if it is the min element
     * @return true if successfully added, otherwise false
     */
    public boolean addIfMin(MusicBand newBand, String username) throws QueryExecutionException {
        readWriteLock.writeLock().lock();
        try{
            try {
                MusicBand minBand;
                minBand = getMin();
                if(newBand.compareTo(minBand) < 0){
                    newBand.setId(musicBandDao.addBandToDb(newBand, username));
                    collection.add(newBand);
                    return true;
                }
            } catch (EmptyCollectionException e) {
                newBand.setId(musicBandDao.addBandToDb(newBand, username));
                collection.add(newBand);
                return true;
            }
            return false;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public boolean checkOwner(int id, String username) throws QueryExecutionException {
        readWriteLock.readLock().lock();
        try{
            return musicBandDao.isOwner(id, username);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * count bands with genre lesser than given genre, bands with no genre are not counted
     * @param genre genre to compare other genres to
     * @return the amount of counted bands
     */
    public int countWithLesserGenre(MusicGenre genre){
        readWriteLock.readLock().lock();
        try{
            return (int) collection.stream().filter(b -> b.getGenre() != null && genre.compareTo(b.getGenre()) > 0).count();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * find all bands with description that starts with a given string
     * @param start start of description
     * @return list of all found bands
     */
    public List<MusicBand> getWithDescriptionStart(String start){
        readWriteLock.readLock().lock();
        try{
            return collection.stream().filter(b -> b.getDescription() != null
                    && b.getDescription().startsWith(start)).collect(Collectors.toList());
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * get list of bands sorted in descending order
     * @return list of bands
     */
    public List<MusicBand> getDescending(){
        readWriteLock.readLock().lock();
        try{
            List<MusicBand> res = new ArrayList<>(collection);
            res.sort(MusicBand::compareTo);
            return res;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public List<MusicBand> getAll(){
        readWriteLock.readLock().lock();
        try{
            return new ArrayList<>(collection);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
