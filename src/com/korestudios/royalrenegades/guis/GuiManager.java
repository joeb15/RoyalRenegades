package com.korestudios.royalrenegades.guis;

import java.util.ArrayList;

public class GuiManager {

    private static ArrayList<Gui> guis = new ArrayList<>();

    public static void addGui(Gui gui){
        guis.add(gui);
    }

    public static ArrayList<Gui> getGuis() {
        return guis;
    }

    public static void update() {
        for(Gui g:guis)
            g.update();
    }

    public static void clearGuis() {
        guis.clear();
    }
}
