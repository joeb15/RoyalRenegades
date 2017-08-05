package com.korestudios.royalrenegades.guis;

import com.korestudios.royalrenegades.guis.components.ButtonComponent;
import com.korestudios.royalrenegades.guis.components.GuiComponent;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static com.korestudios.royalrenegades.constants.GlobalVariables.*;
import static com.korestudios.royalrenegades.font.BitmapFont.FONT_COURIER;

public class SettingGui extends Gui {

    private ButtonComponent buttonComponent, buttonComponent2;

    public SettingGui(){
        buttonComponent = new ButtonComponent(0, FRAME_HEIGHT-50, 350, 50, 5, 40,()->{
            debug_mode=!debug_mode;
        },()->{
            return buttonComponent.isTriggered()
                    ?new Vector4f(1,1,0,0.5f)
                    :debug_mode
                    ?new Vector4f(0,1,0,0.5f)
                    :new Vector4f(1,0,0,0.5f);
        },()->{
            return new Vector4f(1,0,0,1);
        },()->{
            return debug_mode
                    ?"Debug ON"
                    :"Debug OFF";
        }, FONT_COURIER, true);

        buttonComponent2 = new ButtonComponent(0, FRAME_HEIGHT-100, 350, 50, 5, 40,()->{
            limitFrameRate=!limitFrameRate;
        },()->{
            return buttonComponent2.isTriggered()
                    ?new Vector4f(1,1,0,0.5f)
                    :limitFrameRate
                    ?new Vector4f(0,1,0,0.5f)
                    :new Vector4f(1,0,0,0.5f);
        },()->{
            return new Vector4f(1,0,0,1);
        },()->{
            return limitFrameRate
                    ?"FPS Limit On"
                    :"FPS Limit Off";
        }, FONT_COURIER, true);

        add(buttonComponent);
        add(buttonComponent2);
    }

    public void update(){
        super.update();
    }

    public boolean onClick(Vector2f pos){
        for(GuiComponent guiComponent:getComponents())
            if(guiComponent.onClick(pos))
                return true;
        return false;

    }

}
