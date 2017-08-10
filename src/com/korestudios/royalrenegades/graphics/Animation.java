package com.korestudios.royalrenegades.graphics;

import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;
import com.korestudios.royalrenegades.utils.time.TimerUtils;

import static com.korestudios.royalrenegades.constants.ErrorConstants.UNEXPECTED_PARAMETERS_ERROR;
import static com.korestudios.royalrenegades.constants.VariableConstants.DEFAULT_SPRITE_SHEET;
import static com.korestudios.royalrenegades.constants.VariableConstants.DEFAULT_SPRITE_SHEET_COLS;
import static com.korestudios.royalrenegades.constants.VariableConstants.DEFAULT_SPRITE_SHEET_ROWS;

public class Animation {

    private float animTime;
    private int[] xs, ys;
    private SpriteSheet spriteSheet;
    private int index=0;
    private int animLength;
    private int tileWidth=1;
    private int tileHeight=1;

    public Animation(String spriteSheet){
        this(spriteSheet, 1, 1, 0, 0);
    }

    public Animation(int x, int y){
        this(DEFAULT_SPRITE_SHEET, DEFAULT_SPRITE_SHEET_COLS, DEFAULT_SPRITE_SHEET_ROWS, x, y);
    }

    public Animation(String spriteSheet, int ssW, int ssH, int x, int y){
        this(spriteSheet,ssW,ssH,new int[]{x}, new int[]{y}, -1);
    }

    public Animation(int[] xs, int[] ys, float totalAnimTime){
        this(DEFAULT_SPRITE_SHEET, DEFAULT_SPRITE_SHEET_COLS, DEFAULT_SPRITE_SHEET_ROWS, xs, ys, totalAnimTime);
    }

    public Animation(String spriteSheet, int ssW, int ssH, int[] xs, int[] ys, float totalAnimTime){
        if(xs.length!=ys.length){
            Logger.log(PRIORITY.ERRORS, "Animation", "Length of x's and y's do not match", UNEXPECTED_PARAMETERS_ERROR, false);
        }
        this.spriteSheet = SpriteSheet.getSpritesheet(spriteSheet, ssW, ssH);
        this.xs=xs;
        this.ys=ys;
        animLength=xs.length;
        this.animTime=totalAnimTime;
        if(animLength==1)
            return;
        long animTime = (long) (totalAnimTime*1E9);
        TimerUtils.addTimer(()->{
            index++;
            index%=animLength;
        }, animTime/animLength, -1);
    }

    public int getTileWidth(){
        return tileWidth;
    }

    public int getTileHeight(){
        return tileHeight;
    }

    public int getAnimLength(){
        return animLength;
    }

    public float getAnimTime(){
        return animTime;
    }

    public int getTileX(){
        return xs[index];
    }

    public int getTileY(){
        return ys[index];
    }

    public Animation setTileSize(int tileWidth, int tileHeight){
        this.tileWidth=tileWidth;
        this.tileHeight=tileHeight;
        return this;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }


}
