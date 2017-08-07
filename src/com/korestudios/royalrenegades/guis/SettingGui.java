package com.korestudios.royalrenegades.guis;

import com.korestudios.royalrenegades.guis.components.ButtonComponent;
import com.korestudios.royalrenegades.input.Input;
import org.joml.Vector4f;

import static com.korestudios.royalrenegades.constants.GlobalVariables.*;
import static com.korestudios.royalrenegades.font.BitmapFont.FONT_COURIER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class SettingGui extends Gui {

    private boolean show = false;

    public SettingGui(){
        ButtonComponent buttonComponent = new ButtonComponent(0, FRAME_HEIGHT-50, 350, 50, 5, 40,()->
            debug_mode=!debug_mode
        ,()-> debug_mode
                ?new Vector4f(0,.6f,0,1)
                :new Vector4f(.7f,0,0,1)
        ,()-> new Vector4f(0,0,0,1)
        ,()-> debug_mode
                ?"Debug ON"
                :"Debug OFF"
        , FONT_COURIER, show);

        ButtonComponent buttonComponent2 = new ButtonComponent(0, FRAME_HEIGHT-100, 350, 50, 5, 40
        ,()->limitFrameRate=!limitFrameRate
        ,()->limitFrameRate
                ?new Vector4f(0,.6f,0,1)
                :new Vector4f(.7f,0,0,1)
        ,()->new Vector4f(0,0,0,1)
        ,()-> limitFrameRate
                ?"FPS Limit On"
                :"FPS Limit Off"
        , FONT_COURIER, show);

        add(buttonComponent);
        add(buttonComponent2);
        Input.addListener(GLFW_KEY_ESCAPE, Input.TYPE_KEY_RELEASE, ()->{
            if(show = !show){
                show();
            }else{
                hide();
            }
        });
    }

}
