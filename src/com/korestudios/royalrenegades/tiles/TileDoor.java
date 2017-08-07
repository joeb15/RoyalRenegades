package com.korestudios.royalrenegades.tiles;

import com.korestudios.royalrenegades.entities.Entity;
import com.korestudios.royalrenegades.entities.MainEntity;
import com.korestudios.royalrenegades.tiles.metadata.DoorMetaData;
import com.korestudios.royalrenegades.tiles.metadata.MetaData;
import com.korestudios.royalrenegades.world.Chunk;
import com.korestudios.royalrenegades.world.World;

public class TileDoor extends Tile {

    @Override
    public boolean onCollide(World world, Entity collider, MetaData metaData) {
        DoorMetaData md = (DoorMetaData)metaData;
        world.moveEntity(collider, md.nextChunk);
        world.changeChunk(md.nextChunk);
        collider.setPos(md.exitPos.x, md.exitPos.y);
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

    @Override
    public void onPlace(Chunk chunk, Entity placer, MetaData metaData){
        if(placer instanceof MainEntity)
            System.out.println("HELLO WORLD");
    }
    @Override
    public void onDestroy(Chunk chunk, Entity destroyer, MetaData metaData){
        if(destroyer instanceof MainEntity)
            System.out.println("GOODBYE WORLD");
    }
}
