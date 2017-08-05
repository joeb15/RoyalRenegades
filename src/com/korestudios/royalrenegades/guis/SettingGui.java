package com.korestudios.royalrenegades.guis;

import com.korestudios.royalrenegades.guis.components.BackgroundBorderComponent;
import com.korestudios.royalrenegades.guis.components.BackgroundComponent;
import com.korestudios.royalrenegades.guis.components.TextBackgroundComponent;
import com.korestudios.royalrenegades.guis.components.TextComponent;
import com.korestudios.royalrenegades.input.Input;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static com.korestudios.royalrenegades.constants.GlobalVariables.*;
import static com.korestudios.royalrenegades.constants.GlobalVariables.displayed_tps;
import static com.korestudios.royalrenegades.constants.VariableConstants.FRAME_HEIGHT;
import static com.korestudios.royalrenegades.font.BitmapFont.FONT_COURIER;
import static com.korestudios.royalrenegades.font.BitmapFont.FONT_DEJAVU;
import static com.korestudios.royalrenegades.utils.MathUtils.round;
import static com.korestudios.royalrenegades.utils.MathUtils.toTimeString;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class SettingGui extends Gui {

    private float w = 130;
    private float h = 50;
    private float x = 5;
    private float y = FRAME_HEIGHT - h-5;
    private boolean triggered = false;

    public SettingGui(){
        BackgroundComponent bc = new BackgroundComponent(() -> {return new Vector4f(1,1,1,0.5f);}, x,y,w,h, true);
        BackgroundBorderComponent bbc = new BackgroundBorderComponent(bc, ()->{return new Vector4f(1,0,0,1);}, 5, true);
        TextComponent tc = new TextComponent(()->{return "Debug";},FONT_COURIER, x+h/8,y+h/8,h*.75f, true);

        add(bc);
        add(bbc);
        add(tc);
    }

    public void update(){
        if(triggered && !Input.isMouseButtonDown(GLFW_MOUSE_BUTTON_1)) {
            triggered = false;
            debug_mode=!debug_mode;
        }
    }

    public boolean onClick(Vector2f pos){
        if(!triggered && pos.x>x && pos.x<x+w && pos.y>y && pos.y<y+h){
            triggered=true;
            return true;
        }
        return false;
    }

}
