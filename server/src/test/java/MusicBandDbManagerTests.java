import collectionitems.MusicBand;
import collectionitems.WrongArgumentException;
import data.database.DbManagerInitializationException;
import data.database.MusicBandDbManager;
import data.database.QueryExecutionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
            band1.setId(1);
            band1.setName("band1");
            allBands.add(band1);
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
