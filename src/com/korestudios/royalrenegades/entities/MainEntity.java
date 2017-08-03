package com.korestudios.royalrenegades.entities;

import com.korestudios.royalrenegades.graphics.Animation;
import com.korestudios.royalrenegades.input.Input;
import com.korestudios.royalrenegades.physics.CollisionSystem;

import static com.korestudios.royalrenegades.constants.GlobalVariables.CENTER;
import static com.korestudios.royalrenegades.constants.VariableConstants.*;
import static com.korestudios.royalrenegades.utils.MathUtils.moveCloser;
import static org.lwjgl.glfw.GLFW.*;

public class MainEntity extends Entity{

    public MainEntity(float x, float y) {
        super(x,y,
                new Animation(DEFAULT_SPRITE_SHEET, DEFAULT_SPRITE_SHEET_COLS,DEFAULT_SPRITE_SHEET_ROWS,
                        new int[]{6, 7},new int[]{0, 0}, 10f));
        setSize(2,2);
        updateCollBox();
    }

    @Override
    public void setPos(float x, float y){
        super.setPos(x, y);
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
        if(Input.isKeyDown(GLFW_KEY_E)){
            rotation+=3;
        }
        if(Input.isKeyDown(GLFW_KEY_Q)){
            rotation-=3;
        }
        move(x, 0);
        if (CollisionSystem.collides(this))
            move(-x, 0);
        move(0, y);
        if(CollisionSystem.collides(this))
            move(0, -y);
    }

}
