package com.korestudios.royalrenegades.tiles.metadata;

import org.joml.Vector2f;

public class DoorMetaData extends MetaData{
    public boolean left;
    public String nextChunk;
    public Vector2f exitPos;

    public DoorMetaData(boolean left, String nextChunk, Vector2f exitPos){
        this.left=left;
        this.nextChunk=nextChunk;
        this.exitPos=exitPos;
    }
}
