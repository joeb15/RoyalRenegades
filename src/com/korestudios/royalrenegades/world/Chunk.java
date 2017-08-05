package com.korestudios.royalrenegades.world;

import com.korestudios.royalrenegades.entities.Entity;
import com.korestudios.royalrenegades.tiles.Tile;
import com.korestudios.royalrenegades.tiles.TileList;
import com.korestudios.royalrenegades.tiles.metadata.MetaData;
import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;
import org.joml.Vector2f;
import org.joml.Vector4i;

import java.util.ArrayList;

import static com.korestudios.royalrenegades.constants.ErrorConstants.TILE_REQUIRES_METADATA_ERROR;
import static com.korestudios.royalrenegades.constants.GlobalVariables.FRAME_HEIGHT;
import static com.korestudios.royalrenegades.constants.GlobalVariables.FRAME_WIDTH;
import static com.korestudios.royalrenegades.constants.GlobalVariables.TILE_SIZE;

/**
 * Created by joe on 7/15/17.
 */
public abstract class Chunk {

    private Tile[][] tiles;
    protected int w;
    protected int h;
    protected ArrayList<Entity> entities = new ArrayList<Entity>();
    private MetaData[][] metaData;

    public Chunk(int w, int h){
        this.w=w;
        this.h=h;
        tiles = new Tile[h][w];
        metaData = new MetaData[h][w];
        createChunk();
    }

    public abstract void update();
    public abstract void createChunk();

    private int getMin(float delta, float hw, int chunkSize){
        int chunkCenter = chunkSize/2;
        float tilePixelDelta = hw/2 + delta;
        int tileDelta = (int) Math.ceil(tilePixelDelta/TILE_SIZE);
        int tile = chunkCenter - tileDelta;
        return tile;
    }

    private int getMax(float delta, float hw, int chunkSize){
        int chunkCenter = chunkSize/2;
        float tilePixelDelta = hw/2 - delta;
        int tileDelta = (int) Math.ceil(tilePixelDelta/TILE_SIZE);
        int tile = chunkCenter + tileDelta;
        return tile;
    }

    public Vector4i getVisibleArea(Vector2f center){
        float dx = w*TILE_SIZE/2f - center.x;
        float dy = h*TILE_SIZE/2f - center.y;

        int minX=getMin(dx, FRAME_WIDTH, w);
        int maxX=getMax(dx, FRAME_WIDTH, w);
        int minY=getMin(dy, FRAME_HEIGHT, h);
        int maxY=getMax(dy, FRAME_HEIGHT, h);
        return new Vector4i(minX, minY, maxX, maxY);
    }

    public void setTile(int x, int y, Tile t){
        setTile(x, y, t, new MetaData());
    }

    public void setTile(int x, int y, Tile t, MetaData metaData){
        if(t.hasMetaData()&&metaData==null)
            Logger.log(PRIORITY.ERRORS, "Chunk", "Tile was not supplied metadata even though it was required", TILE_REQUIRES_METADATA_ERROR, false);
        tiles[y][x]=t;
        metaData.x=x;
        metaData.y=y;
        this.metaData[y][x]=metaData;
    }

    public Tile getTile(int x, int y){
        if (x >= 0 && y >= 0 && x < w && y < h){
            Tile t = tiles[y][x];
            if(t!=null)
                return t;
        }
        return TileList.tileVoid;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public MetaData getMetadata(int x, int y) {
        if (x >= 0 && y >= 0 && x < w && y < h){
            MetaData m = metaData[y][x];
            return m;
        }
        return null;
    }

    protected void clearCorners(){
        setTile(0,0,TileList.tileVoid);
        setTile(w-1,0,TileList.tileVoid);
        setTile(0,h-1,TileList.tileVoid);
        setTile(w-1,h-1,TileList.tileVoid);
    }

    protected Tile[][] getTiles(){
        return tiles;
    }

    protected MetaData[][] getMetaData(){
        return metaData;
    }
}
