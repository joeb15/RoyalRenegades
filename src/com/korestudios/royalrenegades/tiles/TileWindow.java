package com.korestudios.royalrenegades.tiles;

import com.korestudios.royalrenegades.tiles.metadata.MetaData;
import com.korestudios.royalrenegades.tiles.metadata.WindowMetaData;

public class TileWindow extends Tile {

    public static final int TOP = 0x01;
    public static final int LEFT = 0x02;
    public static final int RIGHT = 0x03;
    public static final int BOTTOM = 0x04;
    private static final int LOC_MASK = 0x0F;

    public static final int CENTER = 0x10;
    public static final int END_L = 0x20;
    public static final int END_R = 0x30;
    private static final int POS_MASK = 0xF0;

    public TileWindow(){
        super.setHasMetaData();
    }

    public int getTileX(MetaData metaData){
        WindowMetaData md = ((WindowMetaData)metaData);
        int orientation = md.orientation;
        switch (orientation&POS_MASK){
            case CENTER:return 1;
            case END_L:return 0;
            case END_R:return 2;
        }
        return 0;
    }

    public int getTileY(MetaData metaData){
        WindowMetaData md = ((WindowMetaData)metaData);
        if(md.hasMob)
            return 2;
        return 1;
    }

    public float getRotation(MetaData metaData){
        WindowMetaData md = (WindowMetaData)metaData;
        switch (md.orientation&LOC_MASK){
            case TOP:return 0;
            case LEFT:return 90;
            case RIGHT:return -90;
            case BOTTOM:return 180;
        }
        return 0;
    }
}
