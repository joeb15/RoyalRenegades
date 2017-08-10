package com.korestudios.royalrenegades.graphics;

public class Image {

    private Animation animation;
    private float x, y, w, h, rot;

    public Image(String imagePath, float x, float y, float w, float h) {
        this(imagePath, x, y, w, h, 0);
    }

    public Image(String imagePath, float x, float y, float w, float h, float rot) {
        this(new Animation(imagePath), x, y, w, h, rot);
    }

    public Image(Animation animation, float x, float y, float w, float h) {
        this(animation, x, y, w, h, 0);
    }

    public Image(Animation animation, float x, float y, float w, float h, float rot) {
        this.animation = animation;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.rot=rot;
    }

    public Animation getAnimation() {
        return animation;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public float getRotation(){
        return rot;
    }
}
