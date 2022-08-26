import collectionitems.*;
import data.database.DbManagerInitializationException;
import data.database.MusicBandDbManager;
import data.database.QueryExecutionException;
import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MusicBandDbManagerTests {
    private MusicBandDbManager musicBandDbManager;
    private List<MusicBand> allBands;

    @Before
    public void init(){
        try{
            musicBandDbManager = new MusicBandDbManager("jdbc:postgresql://localhost:5432/studs",
                    "s264432", "ajf870", "test_prog_musicband_264432");
            allBands = new ArrayList<>();
            MusicBand band1 = new MusicBand();
            band1.setId(2);
            band1.setName("band1");
            Coordinates coordinates1 = new Coordinates();
            coordinates1.setX(1f);
            coordinates1.setY(1f);
            band1.setCoordinates(coordinates1);
            band1.setNumberOfParticipants(3);
            band1.setAlbumsCount(5);
            allBands.add(band1);

            MusicBand band2 = new MusicBand();
            band2.setId(3);
            band2.setName("band2");
            Coordinates coordinates2 = new Coordinates();
            coordinates2.setX(2.05f);
            coordinates2.setY(2.948f);
            band2.setCoordinates(coordinates2);
            band2.setNumberOfParticipants(2);
            band2.setAlbumsCount(7);
            band2.setDescription("band 2 description");
            band2.setGenre(MusicGenre.BLUES);
            Album bestAlbum2 = new Album();
            bestAlbum2.setName("best_album");
            bestAlbum2.setTracks(70132);
            bestAlbum2.setLength(15);
            bestAlbum2.setSales(14.07f);
            band2.setBestAlbum(bestAlbum2);
            allBands.add(band2);
        }
        catch (DbManagerInitializationException | WrongArgumentException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void testGetBandsFromDb(){
        try{
            assertEquals(allBands, musicBandDbManager.getBandsFromDb());
        }
        catch (QueryExecutionException ex){
            ex.printStackTrace();
        }
    }
}
