package com.korestudios.royalrenegades.guis;

import com.korestudios.royalrenegades.guis.components.ButtonComponent;
import com.korestudios.royalrenegades.states.StateManager;

import static com.korestudios.royalrenegades.constants.GlobalVariables.FRAME_HEIGHT;
import static com.korestudios.royalrenegades.constants.GlobalVariables.FRAME_WIDTH;
import static com.korestudios.royalrenegades.font.BitmapFont.FONT_COURIER;

public class SplashGui extends Gui{

    public SplashGui(){
        int w = 400;
        int h = 200;
        ButtonComponent bc = new ButtonComponent("res/button.png", FRAME_WIDTH/2-w/2, FRAME_HEIGHT/2-h/2, w, h, 40
                ,()->StateManager.switchState(StateManager.gameState)
                ,()->"Start Game!"
                ,FONT_COURIER, true);
        add(bc);
    }
}
