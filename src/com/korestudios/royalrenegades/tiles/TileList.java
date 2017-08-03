package com.korestudios.royalrenegades.tiles;

public class TileList {
    public static final Tile tileVoid = new Tile(3,0).setCollides(false);
    public static final Tile tileFloor = new Tile(2, 0).setCollides(false);
    public static final Tile tileWall = new TileWall().setHasMetaData();
    public static final Tile tileWindow = new TileWindow().setHasMetaData();
    public static final Tile tileDoor = new TileDoor().setHasMetaData();
}