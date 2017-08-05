package com.korestudios.royalrenegades.guis;

import com.korestudios.royalrenegades.guis.components.TextBackgroundComponent;
import com.korestudios.royalrenegades.guis.components.TextComponent;
import org.joml.Vector4f;

import static com.korestudios.royalrenegades.constants.GlobalVariables.*;
import static com.korestudios.royalrenegades.constants.GlobalVariables.displayed_tps;
import static com.korestudios.royalrenegades.font.BitmapFont.FONT_COURIER;
import static com.korestudios.royalrenegades.font.BitmapFont.FONT_DEJAVU;
import static com.korestudios.royalrenegades.utils.MathUtils.round;
import static com.korestudios.royalrenegades.utils.MathUtils.toTimeString;

public class DebugGui extends Gui {

    public DebugGui(){
        TextComponent tc = new TextComponent(()->{return "Render: " + toTimeString(displayed_render_time) + "\n" +
                "Update: " + toTimeString(displayed_update_time) + "\n" +
                "Tris: " + triangles_drawn_last_frame + "\n" +
                "Fps: " + round(displayed_fps, 1) + "\n" +
                "Tps: " + round(displayed_tps, 1);},
                FONT_COURIER, 0, 0, 25, true);

        TextBackgroundComponent tbc = new TextBackgroundComponent(tc, ()-> {
            return new Vector4f(0,0,0, 0.5f);
        }, true);
        add(tc);
        add(tbc);
    }

    public void update(){
        if(debug_mode)
            show();
        else
            hide();
    }

}
