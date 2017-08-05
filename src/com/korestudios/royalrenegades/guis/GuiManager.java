package com.korestudios.royalrenegades.guis;

import org.joml.Vector2f;

import java.util.ArrayList;

public class GuiManager {

    private static ArrayList<Gui> guis = new ArrayList<Gui>();;

    public static void addGui(Gui gui){
        guis.add(gui);
    }

    public static ArrayList<Gui> getGuis() {
        return guis;
    }

    public static void onClick(Vector2f cursorPos) {
        for(Gui g:guis)
            if(g.onClick(cursorPos))
                return;
    }

    public static void update() {
        for(Gui g:guis)
            g.update();
    }
}
