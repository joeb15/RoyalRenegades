package com.korestudios.royalrenegades.guis;

import java.util.ArrayList;

public class GuiManager {

    private ArrayList<Gui> guis;

    public GuiManager(){
        guis = new ArrayList<Gui>();
    }

    public void addGui(Gui gui){
        guis.add(gui);
    }

    public ArrayList<Gui> getGuis() {
        return guis;
    }
}
