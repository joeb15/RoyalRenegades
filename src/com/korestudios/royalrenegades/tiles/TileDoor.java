package com.korestudios.royalrenegades.tiles;

import com.korestudios.royalrenegades.entities.Entity;
import com.korestudios.royalrenegades.tiles.metadata.DoorMetaData;
import com.korestudios.royalrenegades.tiles.metadata.MetaData;
import com.korestudios.royalrenegades.world.World;

public class TileDoor extends Tile {

    @Override
    public boolean onCollide(World world, Entity collider, MetaData metaData) {
        DoorMetaData md = (DoorMetaData)metaData;
        world.changeChunk(md.nextChunk);
        collider.setPos(5,5);
        return true;
    }

    @Override
    public int getTileX(MetaData metaData){
        DoorMetaData md = (DoorMetaData)metaData;
        if(md.left)
            return 4;
        return 5;
    }

    @Override
    public int getTileY(MetaData metaData){
        return 0;
    }
}
