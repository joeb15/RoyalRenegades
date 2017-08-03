package com.korestudios.royalrenegades.world.chunks;

import com.korestudios.royalrenegades.entities.Entity;
import com.korestudios.royalrenegades.entities.MainEntity;
import com.korestudios.royalrenegades.graphics.Animation;
import com.korestudios.royalrenegades.input.Input;
import com.korestudios.royalrenegades.physics.CollisionSystem;
import com.korestudios.royalrenegades.tiles.Tile;
import com.korestudios.royalrenegades.tiles.TileList;
import com.korestudios.royalrenegades.tiles.TileWindow;
import com.korestudios.royalrenegades.tiles.metadata.DoorMetaData;
import com.korestudios.royalrenegades.tiles.metadata.MetaData;
import com.korestudios.royalrenegades.tiles.metadata.WallMetaData;
import com.korestudios.royalrenegades.tiles.metadata.WindowMetaData;
import com.korestudios.royalrenegades.world.Chunk;

import static com.korestudios.royalrenegades.constants.GlobalVariables.CENTER;
import static com.korestudios.royalrenegades.constants.VariableConstants.*;
import static org.lwjgl.glfw.GLFW.*;

public class ChunkStart extends Chunk{
    public ChunkStart() {
        super(20,12);
    }

    public void createChunk() {
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                if(y==0||y==h-1)
                    setTile(x, y, TileList.tileWall, new WallMetaData(false));
                else if(x==0||x==w-1)
                    setTile(x, y, TileList.tileWall, new WallMetaData(true));
                else
                    setTile(x, y, TileList.tileFloor);
            }
        }

        setTile(2,0, TileList.tileWindow, new WindowMetaData(TileWindow.TOP|TileWindow.END_L, true));
        setTile(3,0, TileList.tileWindow, new WindowMetaData(TileWindow.TOP|TileWindow.CENTER, true));
        setTile(4,0, TileList.tileWindow, new WindowMetaData(TileWindow.TOP|TileWindow.CENTER, true));
        setTile(5,0, TileList.tileWindow, new WindowMetaData(TileWindow.TOP|TileWindow.CENTER, true));
        setTile(6,0, TileList.tileWindow, new WindowMetaData(TileWindow.TOP|TileWindow.CENTER, true));
        setTile(7,0, TileList.tileWindow, new WindowMetaData(TileWindow.TOP|TileWindow.END_R, true));

        setTile(9,0,TileList.tileDoor, new DoorMetaData(true, "Start"));
        setTile(10,0,TileList.tileDoor, new DoorMetaData(false, "Start"));

        setTile(12,0, TileList.tileWindow, new WindowMetaData(TileWindow.TOP|TileWindow.END_L, false));
        setTile(13,0, TileList.tileWindow, new WindowMetaData(TileWindow.TOP|TileWindow.CENTER, false));
        setTile(14,0, TileList.tileWindow, new WindowMetaData(TileWindow.TOP|TileWindow.CENTER, false));
        setTile(15,0, TileList.tileWindow, new WindowMetaData(TileWindow.TOP|TileWindow.CENTER, false));
        setTile(16,0, TileList.tileWindow, new WindowMetaData(TileWindow.TOP|TileWindow.CENTER, false));
        setTile(17,0, TileList.tileWindow, new WindowMetaData(TileWindow.TOP|TileWindow.END_R, false));
        clearCorners();

        entities.add(new MainEntity(4.5f, 4.5f));
    }

    public void update(){
        CollisionSystem.clear();
        Tile[][] tiles = getTiles();
        MetaData[][] metaData = getMetaData();
        for(int i=0;i<getHeight();i++){
            for(int j=0;j<getWidth();j++){
                CollisionSystem.addTile(tiles[i][j], metaData[i][j]);
            }
        }
        for(Entity e:entities)
            e.update();
    }
}
