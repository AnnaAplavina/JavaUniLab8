package connection;

import collectionitems.MusicBand;

import java.io.Serializable;
import java.util.List;

public class MusicBandResponse implements Serializable {
    public ResponseStatus status = null;
    public String response = null;
    public List<MusicBand> musicBandList = null;
    public List<Integer> ids = null;
}
