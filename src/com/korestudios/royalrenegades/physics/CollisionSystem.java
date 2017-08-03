package com.korestudios.royalrenegades.physics;

import com.korestudios.royalrenegades.entities.Entity;
import com.korestudios.royalrenegades.tiles.Tile;
import com.korestudios.royalrenegades.tiles.metadata.MetaData;
import com.korestudios.royalrenegades.world.World;

import java.util.HashMap;

public class CollisionSystem {

    private static HashMap<MetaData, Tile> tiles = new HashMap<>();

    private static World world;

    public static boolean collides(Entity entity){
        boolean collides=false;
        for(MetaData md:tiles.keySet()){
            if(entity.getCollisionBox().collides(md.x, md.y, 1, 1)){
                Tile t = tiles.get(md);
                t.onCollide(world, entity, md);
                if(t.doesCollide())
                    collides=true;
            }
        }
        return collides;
    }

    public static void addTile(Tile t, MetaData md){
        tiles.put(md, t);
    }

    public static void init(World world){
        CollisionSystem.world=world;
    }

    public static void clear(){
        tiles.clear();
    }

}
