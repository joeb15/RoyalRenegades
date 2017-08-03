package com.korestudios.royalrenegades.tiles.metadata;

public class DoorMetaData extends MetaData{
    public boolean left;
    public String nextChunk;
    public DoorMetaData(boolean left, String nextChunk){
        this.left=left;
        this.nextChunk=nextChunk;
    }
}
