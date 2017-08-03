package com.korestudios.royalrenegades.physics;

public class CollisionBox {

    private float x,y,w,h;

    public CollisionBox(float x, float y, float w, float h){
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
    }

    public boolean collides(float x, float y, float w, float h){
        return Math.abs(x-this.x)*2<this.w+w &&
                Math.abs(y-this.y)*2<this.h+h;
    }

}
