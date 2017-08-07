package com.korestudios.royalrenegades.guis;

import com.korestudios.royalrenegades.guis.components.GuiComponent;

import java.util.ArrayList;

public class Gui {

    private ArrayList<GuiComponent> components = new ArrayList<>();

    void show(){
        for(GuiComponent c:components)
            c.setShowing(true);
    }

    void hide(){
        for(GuiComponent c:components)
            c.setShowing(false);
    }

    public void update(){
        for(GuiComponent c:components)
            c.update();
    }

    public void add(GuiComponent component){
        components.add(component);
    }

    public ArrayList<GuiComponent> getComponents(){
        return components;
    }
}
