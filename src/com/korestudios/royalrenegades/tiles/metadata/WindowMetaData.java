package com.korestudios.royalrenegades.tiles.metadata;

public class WindowMetaData extends MetaData{
    public int orientation;
    public boolean hasMob;

    public WindowMetaData(int orientation, boolean hasMob){
        this.orientation=orientation;
        this.hasMob=hasMob;
    }
}
