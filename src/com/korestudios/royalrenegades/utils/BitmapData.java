package com.korestudios.royalrenegades.utils;

import org.joml.Vector2f;
import java.util.HashMap;

public class BitmapData{
    public Vector2f pos;
    public Vector2f size;
    public Vector2f offset;
    public float advance;
    public int page;

    public HashMap<Character, Integer> kernings = new HashMap<Character, Integer>();

    public BitmapData(int x, int y, int width, int height, int xOff, int yOff, int adv, int page) {
        pos = new Vector2f(x, y);
        size = new Vector2f(width, height);
        offset = new Vector2f(xOff, yOff);
        advance = adv;
        this.page=page;
    }
}