package com.korestudios.royalrenegades.tiles;

import com.korestudios.royalrenegades.tiles.metadata.MetaData;
import com.korestudios.royalrenegades.tiles.metadata.WallMetaData;

public class TileWall extends Tile {

    public int getTileX(MetaData metaData){
        return 0;
    }

    public int getTileY(MetaData metaData){
        return 0;
    }

    public float getRotation(MetaData metaData){
        WallMetaData md = (WallMetaData) metaData;
        if(md.horizontal)
            return 90;
        return 0;
    }
}
