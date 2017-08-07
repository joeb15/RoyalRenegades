package com.korestudios.royalrenegades.world;

import com.korestudios.royalrenegades.entities.Entity;
import com.korestudios.royalrenegades.physics.CollisionSystem;
import com.korestudios.royalrenegades.world.chunks.ChunkStart;
import com.korestudios.royalrenegades.world.chunks.ChunkStart2;

import java.util.HashMap;

/**
 * Created by joe on 7/13/17.
 */
public class World {

    private HashMap<String, Chunk> chunkList = new HashMap<>();

    public static Chunk ACTIVE_CHUNK;

    public World(){
        chunkList.put("Start", new ChunkStart());
        chunkList.put("Start2", new ChunkStart2());

        ACTIVE_CHUNK=chunkList.get("Start");
        CollisionSystem.init(this);
    }

    public void changeChunk(String chunkName){
        ACTIVE_CHUNK = chunkList.get(chunkName);
    }

    public void update(){
        ACTIVE_CHUNK.update();
    }

    public Chunk getCurrChunk() {
        return ACTIVE_CHUNK;
    }

    public void moveEntity(Entity collider, String nextChunk) {
        chunkList.get(nextChunk).addEntity(ACTIVE_CHUNK.removeEntity(collider));
    }

}
