package gui.collectiontable;

import javax.swing.*;

public class DescriptionRowFilter extends RowFilter {
    private String descriptionStart;

    public DescriptionRowFilter(String descriptionStart){
        if(descriptionStart == null){
            throw new IllegalArgumentException();
        }
        this.descriptionStart = descriptionStart;
    }

    @Override
    public boolean include(Entry entry) {
        return entry.getStringValue(7).startsWith(descriptionStart);
    }
}
