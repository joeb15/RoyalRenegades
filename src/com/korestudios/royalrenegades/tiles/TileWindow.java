package com.korestudios.royalrenegades.tiles;

import com.korestudios.royalrenegades.tiles.metadata.MetaData;
import com.korestudios.royalrenegades.tiles.metadata.WindowMetaData;
import com.korestudios.royalrenegades.world.Chunk;
import org.joml.Vector2i;

public class TileWindow extends Tile {

    public static final int TOP = 0x01;
    public static final int LEFT = 0x02;
    public static final int RIGHT = 0x03;
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
        int shift=0;
        if(md.hasMob)
            shift=3;
        switch (orientation&LOC_MASK){
            case TOP:
                switch (orientation&POS_MASK){
                    case CENTER:return 1+shift;
                    case END_L:return 0+shift;
                    case END_R:return 2+shift;
                }
            case LEFT:return 1+shift;
            case RIGHT:return 0+shift;
        }
        return 0;
    }

    public int getTileY(MetaData metaData){
        int orientation = ((WindowMetaData)metaData).orientation;

        switch (orientation&LOC_MASK){
            case TOP:return 1;
            case LEFT:
                switch (orientation&POS_MASK){
                    case CENTER:return 3;
                    case END_L:return 4;
                    case END_R:return 2;
                }
            case RIGHT:
                switch (orientation&POS_MASK){
                    case CENTER:return 3;
                    case END_L:return 2;
                    case END_R:return 4;
                }
        }
        return 0;
    }
}
