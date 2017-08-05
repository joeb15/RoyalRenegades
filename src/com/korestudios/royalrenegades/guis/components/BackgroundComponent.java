package com.korestudios.royalrenegades.guis.components;

import org.joml.Vector4f;

public class BackgroundComponent extends GuiComponent{
    private float x, y, w, h;
    private ColorInterface colorInterface;

    public BackgroundComponent(ColorInterface colorInterface, float x, float y, float w, float h, boolean show) {
        super(show);
        this.colorInterface = colorInterface;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setW(float w) {
        this.w = w;
    }

    public void setH(float h) {
        this.h = h;
    }

    public Vector4f getColor() {
        return colorInterface.getColor();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

}
