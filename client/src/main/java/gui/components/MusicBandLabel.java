package gui.components;

import collectionitems.MusicBand;

import javax.swing.*;
import java.awt.*;

public class MusicBandLabel extends JLabel {
    private MusicBand band;

    public MusicBandLabel(MusicBand band){
        super(band.getName());
        this.band = band;
        setFont(new Font("Serif", Font.PLAIN, (int)band.getAlbumsCount()*2));
        setSize((int)band.getAlbumsCount()*100, 50);
        setLocation(590 + (int)band.getCoordinates().getX().floatValue(), (int)band.getCoordinates().getY());
    }

    public int getId(){
        return band.getId();
    }

    public String getOwnerUsername(){
        return band.getOwnerUsername();
    }
}
