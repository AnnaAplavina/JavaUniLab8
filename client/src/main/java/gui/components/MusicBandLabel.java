package gui.components;

import collectionitems.MusicBand;

import javax.swing.*;

public class MusicBandLabel extends JLabel {
    private MusicBand band;

    public MusicBandLabel(MusicBand band){
        super(band.getName());
        this.band = band;
    }

    public int getId(){
        return band.getId();
    }

    public String getOwnerUsername(){
        return band.getOwnerUsername();
    }
}
