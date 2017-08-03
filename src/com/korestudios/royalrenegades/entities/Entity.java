package com.korestudios.royalrenegades.entities;

import com.korestudios.royalrenegades.graphics.Animation;
import com.korestudios.royalrenegades.graphics.SpriteSheet;
import com.korestudios.royalrenegades.physics.CollisionBox;
import com.korestudios.royalrenegades.shaders.Shader;
import org.joml.Vector2f;

import static com.korestudios.royalrenegades.constants.GlobalVariables.CENTER;
import static com.korestudios.royalrenegades.constants.VariableConstants.*;
import static com.korestudios.royalrenegades.constants.VariableConstants.TILE_SIZE;

public class Entity {

    protected Vector2f pos;
    protected SpriteSheet spriteSheet;
    protected int tileX, tileY;
    protected boolean isAnimated=false;
    protected Animation animation;
    protected int tileHeight=1, tileWidth=1;
    protected CollisionBox collisionBox;

    public Entity(float x, float y, Animation animation){
        this.pos = new Vector2f(x, y);
        this.tileX=0;
        this.tileY=0;
        isAnimated=true;
        this.animation=animation;
        this.spriteSheet=animation.getSpriteSheet();
        updateCollBox();
    }

    public Entity(float x, float y, int tileX, int tileY){
        this(x, y, tileX, tileY, DEFAULT_SPRITE_SHEET, DEFAULT_SPRITE_SHEET_COLS, DEFAULT_SPRITE_SHEET_ROWS);
    }

    public Entity(float x, float y, int tileX, int tileY, String spriteSheet, int ssW, int ssH){
        this.pos = new Vector2f(x, y);
        this.tileX=tileX;
        this.tileY=tileY;
        this.spriteSheet = SpriteSheet.getSpritesheet(spriteSheet, ssW, ssH);
        updateCollBox();
    }

    protected void updateCollBox(){
        this.collisionBox = new CollisionBox(pos.x+tileWidth/4f, pos.y+5*tileHeight/8f, tileWidth/2f, tileHeight/4f);
    }

    public void setPos(float x, float y){
        pos.x=x;
        pos.y=y;
        updateCollBox();
    }

    public Vector2f getPos(){
        return pos;
    }

    public int getTileX(){
        if(isAnimated)
            return animation.getTileX();
        return tileX;
    }

    public int getTileY(){
        if(isAnimated)
            return animation.getTileY();
        return tileY;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public int getTileWidth(){
        return tileWidth;
    }

    public int getTileHeight(){
        return tileHeight;
    }

    public void move(float x, float y) {
        pos.x+=x;
        pos.y+=y;
        updateCollBox();
    }

    public CollisionBox getCollisionBox(){
        return collisionBox;
    }

    public Entity setSize(int w, int h) {
        this.tileWidth=w;
        this.tileHeight=h;
        updateCollBox();
        return this;
    }

    public void update() {
    }

    public void loadTransform(Shader shader){
        float sclX = tileWidth*TILE_SIZE;
        float sclY = tileHeight*TILE_SIZE;
        float x = pos.x*TILE_SIZE+FRAME_WIDTH/2-CENTER.x;
        float y = pos.y*TILE_SIZE+FRAME_HEIGHT/2-CENTER.y;
        shader.load(
                x, y, sclX, sclY
        );
    }
}
