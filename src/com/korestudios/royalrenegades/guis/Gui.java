package com.korestudios.royalrenegades.guis;

import java.util.ArrayList;

import static com.korestudios.royalrenegades.constants.GlobalVariables.*;
import static com.korestudios.royalrenegades.constants.GlobalVariables.displayed_tps;
import static com.korestudios.royalrenegades.font.BitmapFont.FONT_DEJAVU;
import static com.korestudios.royalrenegades.utils.MathUtils.round;
import static com.korestudios.royalrenegades.utils.MathUtils.toTimeString;

public class Gui {

    private ArrayList<TextComponent> textComponents = new ArrayList<TextComponent>();

    public Gui(){
        add(new TextComponent(()->{return "Render: " + toTimeString(displayed_render_time) + "\n" +
                "Update: " + toTimeString(displayed_update_time) + "\n" +
                "Tris: " + triangles_drawn_last_frame + "\n" +
                "Fps: " + round(displayed_fps, 1) + "\n" +
                "Tps: " + round(displayed_tps, 1);},
                FONT_DEJAVU, 0, 0, 25, true));
    }

    public void add(TextComponent textComponent){
        textComponents.add(textComponent);
    }

    public ArrayList<TextComponent> getTextComponents(){
        return textComponents;
    }

}
