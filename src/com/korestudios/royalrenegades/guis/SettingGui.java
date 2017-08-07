package com.korestudios.royalrenegades.guis;

import com.korestudios.royalrenegades.guis.components.ButtonComponent;
import com.korestudios.royalrenegades.guis.components.GuiComponent;
import com.korestudios.royalrenegades.input.Input;
import com.korestudios.royalrenegades.world.World;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static com.korestudios.royalrenegades.constants.GlobalVariables.*;
import static com.korestudios.royalrenegades.font.BitmapFont.FONT_COURIER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class SettingGui extends Gui {

    private ButtonComponent buttonComponent, buttonComponent2;

    private boolean show = false;

    public SettingGui(){
        buttonComponent = new ButtonComponent(0, FRAME_HEIGHT-50, 350, 50, 5, 40,()->{
            debug_mode=!debug_mode;
        },()->{
            return debug_mode
                    ?new Vector4f(0,.6f,0,1)
                    :new Vector4f(.7f,0,0,1);
        },()->{
            return new Vector4f(0,0,0,1);
        },()->{
            return debug_mode
                    ?"Debug ON"
                    :"Debug OFF";
        }, FONT_COURIER, show);

        buttonComponent2 = new ButtonComponent(0, FRAME_HEIGHT-100, 350, 50, 5, 40,()->{
            limitFrameRate=!limitFrameRate;
        },()->{
            return limitFrameRate
                    ?new Vector4f(0,.6f,0,1)
                    :new Vector4f(.7f,0,0,1);
        },()->{
            return new Vector4f(0,0,0,1);
        },()->{
            return limitFrameRate
                    ?"FPS Limit On"
                    :"FPS Limit Off";
        }, FONT_COURIER, show);

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
