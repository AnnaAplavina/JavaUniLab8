package data;

import collectionitems.MusicBand;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

public class DataManager {
    private static final Logger logger = Logger.getLogger(DataManager.class.getName());

    private final String file;

    /**
     * constructor for this class
     * @param fileName name of the xml file where the collection is stored
     */
    public DataManager(String fileName){
        file = fileName;
    }

    /**
     * read collection from file
     * @return collection
     * @throws IOException this exception is thrown if file could not be read
     */
    public Vector<MusicBand> getCollection() throws IOException {
        Vector<MusicBand> collection = new Vector<>();

        try(FileInputStream fis = new FileInputStream(file)) {
            File fileObj = new File(this.file);
            if(fileObj.length() == 0L || !fileObj.exists()){
                return new Vector<>();
            }
            JAXBContext context = JAXBContext.newInstance(MusicBandsCollection.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            MusicBandsCollection toFileBands = (MusicBandsCollection) unmarshaller.unmarshal(fis);
            if(toFileBands.getBands() != null){
                collection.addAll(toFileBands.getBands());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        logger.info("Read collection from file " + file);
        return collection;
    }

    /**
     * save collection to file
     * @param collection collection that will be saved to the file
     * @throws IOException this exception is thrown if writing to the file was not possible
     */
    public void saveCollection(Vector<MusicBand> collection) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(file)) {
            JAXBContext context = JAXBContext.newInstance(MusicBandsCollection.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            MusicBandsCollection toFileBands = new MusicBandsCollection();
            toFileBands.setBands(new ArrayList<>(collection));
            marshaller.marshal(toFileBands, fos);
            logger.info("Save collection to file " + file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class MusicBandsCollection{
        @XmlElement(name="band")
        List<MusicBand> bands;

        public List<MusicBand> getBands(){
            return bands;
        }

        public void setBands(List<MusicBand> bands){
            this.bands = bands;
        }
    }
}
