package gui.collectiontable;

import collectionitems.MusicBand;
import collectionitems.MusicGenre;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MusicBandTableModel extends AbstractTableModel {
    private List<MusicBand> bands;

    public MusicBandTableModel(List<MusicBand> bands){
        this.bands = bands;
    }

    @Override
    public int getRowCount() {
        return bands.size();
    }

    @Override
    public int getColumnCount() {
        return 10;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MusicBand band = bands.get(rowIndex);
        if (columnIndex == 0) {
            return band.getId();
        }
        if (columnIndex == 1) {
            return band.getName();
        }
        if (columnIndex == 2) {
            return band.getCoordinates().getX();
        }
        if (columnIndex == 3) {
            return band.getCoordinates().getY();
        }
        if (columnIndex == 4) {
            return band.getCreationDate().toString();
        }
        if (columnIndex == 5) {
            return band.getNumberOfParticipants();
        }
        if (columnIndex == 6) {
            return band.getAlbumsCount();
        }
        if (columnIndex == 7) {
            return band.getDescription();
        }
        if (columnIndex == 8) {
            MusicGenre genre = band.getGenre();
            if (genre == null) {
                return null;
            }
            if (genre == MusicGenre.BLUES) {
                return "Blues";
            }
            if (genre == MusicGenre.SOUL) {
                return "Soul";
            }
            if (genre == MusicGenre.PUNK_ROCK) {
                return "Punk Rock";
            }
            if (genre == MusicGenre.POST_PUNK) {
                return "Post Punk";
            }
            if (genre == MusicGenre.BRIT_POP) {
                return "Brit Pop";
            }
        }
        if (columnIndex == 9) {
            if (band.getBestAlbum() == null) {
                return null;
            } else {
                return new String[]{"Name: " + band.getBestAlbum().getName(),
                        "Length: " + band.getBestAlbum().getLength(),
                        "Tracks: " + band.getBestAlbum().getTracks(),
                        "Sales: " + band.getBestAlbum().getSales()
                };
            }
        }
        return "!";
    }

    @Override
    public String getColumnName(int c){
        if(c == 0){
            return "ID";
        }
        if(c==1){
            return "Name";
        }
        if(c==2){
            return "X";
        }
        if(c==3){
            return "Y";
        }
        if(c==4){
            return "Creation Date";
        }
        if(c==5){
            return "Number Of Participants";
        }
        if(c==6){
            return "Albums Count";
        }
        if(c==7){
            return "Description";
        }
        if(c==8){
            return "Genre";
        }
        else {
            return "Best Album";
        }
    }

    @Override
    public Class<?> getColumnClass(int c){
        if(c == 9){
            return String[].class;
        }
        return String.class;
    }
}
