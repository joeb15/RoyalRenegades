package com.korestudios.royalrenegades.entities;

import com.korestudios.royalrenegades.graphics.Animation;
import com.korestudios.royalrenegades.input.Input;
import com.korestudios.royalrenegades.physics.CollisionSystem;
import com.korestudios.royalrenegades.sound.SoundManager;

import static com.korestudios.royalrenegades.constants.GlobalVariables.*;
import static com.korestudios.royalrenegades.utils.MathUtils.moveCloser;
import static org.lwjgl.glfw.GLFW.*;

public class MainEntity extends Entity{

    public MainEntity(float x, float y) {
        super(x,y,
                new Animation(new int[]{6, 7},new int[]{0, 0}, 10f));
        setSize(2,2);
        updateCollBox();

//        Input.addListener(GLFW_MOUSE_BUTTON_LEFT, Input.TYPE_MOUSE_RELEASE, ()->{
//            Vector2i pos = Chunk.getMouseTile();
//            World.ACTIVE_CHUNK.removeTile(pos.x, pos.y, this);
//        });
//        Input.addListener(GLFW_MOUSE_BUTTON_RIGHT, Input.TYPE_MOUSE_RELEASE, ()->{
//            Vector2i pos = Chunk.getMouseTile();
//            World.ACTIVE_CHUNK.placeTile(pos.x, pos.y, TileList.tileDoor, this, new DoorMetaData(true, "Start2", new Vector2f(pos.x/TILE_SIZE, pos.y/TILE_SIZE)));
//        });
    }

    @Override
    public void updateCollBox(){
        super.updateCollBox();
        CENTER.x = moveCloser(CENTER.x, (pos.x+width/2)*TILE_SIZE, 1/32f);
        CENTER.y = moveCloser(CENTER.y, (pos.y+height/2)*TILE_SIZE, 1/32f);
    }

    @Override
    public void update(){
        float x=0,y=0;
        if(Input.isKeyDown(GLFW_KEY_W)){
            y-=ENTITY_SPEED;
        }
        if(Input.isKeyDown(GLFW_KEY_S)){
            y+=ENTITY_SPEED;
        }
        if(Input.isKeyDown(GLFW_KEY_A)){
            x-=ENTITY_SPEED;
        }
        if(Input.isKeyDown(GLFW_KEY_D)){
            x+=ENTITY_SPEED;
        }
        move(x, 0);
        if (CollisionSystem.collides(this)) {
            move(-x, 0);
            x=0;
        }
        move(0, y);
        if(CollisionSystem.collides(this)) {
            move(0, -y);
            y=0;
        }
        SoundManager.setListenerPosAndVel(pos.x, pos.y, x, y);
    }

}
