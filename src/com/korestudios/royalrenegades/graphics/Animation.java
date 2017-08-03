package com.korestudios.royalrenegades.graphics;

import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;
import com.korestudios.royalrenegades.utils.time.TimerUtils;

import static com.korestudios.royalrenegades.constants.ErrorConstants.UNEXPECTED_PARAMETERS_ERROR;

public class Animation {

    private float animTime;
    private int[] xs, ys;
    private SpriteSheet spriteSheet;
    private int index=0;
    private int animLength;

    public Animation(String spriteSheet, int ssW, int ssH, int[] xs, int[] ys, float totalAnimTime){
        if(xs.length!=ys.length){
            Logger.log(PRIORITY.ERRORS, "Animation", "Length of x's and y's do not match", UNEXPECTED_PARAMETERS_ERROR, false);
        }
        this.spriteSheet = SpriteSheet.getSpritesheet(spriteSheet, ssW, ssH);
        this.xs=xs;
        this.ys=ys;
        animLength=xs.length;
        this.animTime=totalAnimTime;
        long animTime = (long) (totalAnimTime*1E9);
        TimerUtils.addTimer(()->{
            index++;
            index%=animLength;
        }, animTime/animLength, -1);
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

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }


}
