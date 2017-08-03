package com.korestudios.royalrenegades.entities;

import com.korestudios.royalrenegades.graphics.Animation;
import com.korestudios.royalrenegades.input.Input;
import com.korestudios.royalrenegades.physics.CollisionSystem;

import static com.korestudios.royalrenegades.constants.GlobalVariables.CENTER;
import static com.korestudios.royalrenegades.constants.VariableConstants.*;
import static com.korestudios.royalrenegades.constants.VariableConstants.TILE_SIZE;
import static org.lwjgl.glfw.GLFW.*;

public class MainEntity extends Entity{

    public MainEntity() {
        super(4.25f,4.25f,
                new Animation(DEFAULT_SPRITE_SHEET, DEFAULT_SPRITE_SHEET_COLS,DEFAULT_SPRITE_SHEET_ROWS,
                        new int[]{6, 8},new int[]{0, 0}, 10f));
        setSize(2,2);
        updateCollBox();
    }

    @Override
    public void updateCollBox(){
        super.updateCollBox();
        CENTER.x = pos.x*TILE_SIZE;
        CENTER.y = pos.y*TILE_SIZE;
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
        if (CollisionSystem.collides(this))
            move(-x, 0);
        move(0, y);
        if(CollisionSystem.collides(this))
            move(0, -y);
    }

}
