package com.korestudios.royalrenegades.graphics;

import java.util.HashMap;

public class SpriteSheet extends Texture {
    private int w, h;

    private static HashMap<String, SpriteSheet> hashedTextures = new HashMap<String, SpriteSheet>();

    public SpriteSheet(String path, int w, int h) {
        super(path);
        this.w=w;
        this.h=h;
    }

    public static SpriteSheet getSpritesheet(String path, int w, int h){
        if(!hashedTextures.containsKey(path))
            hashedTextures.put(path, new SpriteSheet(path, w, h));
        return hashedTextures.get(path);
    }

    public int getCols(){
        return w;
    }

    public int getRows(){
        return h;
    }

}
