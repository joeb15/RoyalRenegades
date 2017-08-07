package com.korestudios.royalrenegades.tiles;

import com.korestudios.royalrenegades.entities.Entity;
import com.korestudios.royalrenegades.graphics.SpriteSheet;
import com.korestudios.royalrenegades.tiles.metadata.MetaData;
import com.korestudios.royalrenegades.world.Chunk;
import com.korestudios.royalrenegades.world.World;

import static com.korestudios.royalrenegades.constants.VariableConstants.*;

public class Tile {

    private SpriteSheet texture;
    private boolean hasMetaData = false;
    private int xPos, yPos;
    private boolean collides = true;

    public Tile(String texturePath, int ssW, int ssH, int xPos, int yPos){
        this.xPos=xPos;
        this.yPos=yPos;
        texture = SpriteSheet.getSpritesheet(texturePath, ssW, ssH);
    }

    public Tile(String texturePath, int ssW, int ssH){
        this(texturePath, ssW, ssH, 0, 0);
    }

    public Tile(int xPos, int yPos){
        this(DEFAULT_SPRITE_SHEET, DEFAULT_SPRITE_SHEET_COLS, DEFAULT_SPRITE_SHEET_ROWS, xPos, yPos);
    }

    public Tile(){
        this(DEFAULT_SPRITE_SHEET, DEFAULT_SPRITE_SHEET_COLS, DEFAULT_SPRITE_SHEET_ROWS, 0, 0);
    }

    public void onPlace(Chunk chunk, Entity placer, MetaData metaData){

    }

    public void onDestroy(Chunk chunk, Entity destroyer, MetaData metaData){

    }

    public boolean onCollide(World world, Entity collider, MetaData metaData){
        return collides;
    }

    public SpriteSheet getSpriteSheet() {
        return texture;
    }

    public int getTileX(MetaData metaData){
        return xPos;
    }

    public int getTileY(MetaData metaData){
        return yPos;
    }

    public boolean hasMetaData(){
        return hasMetaData;
    }

    public Tile setHasMetaData() {
        hasMetaData=true;
        return this;
    }

    public Tile setCollides(boolean collides) {
        this.collides = collides;
        return this;
    }

    public boolean doesCollide() {
        return collides;
    }

    public float getRotation(MetaData metaData) {
        return 0;
    }
}
