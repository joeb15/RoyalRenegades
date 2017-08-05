package com.korestudios.royalrenegades.guis;

import com.korestudios.royalrenegades.guis.components.GuiComponent;
import org.joml.Vector2f;

import java.util.ArrayList;

public class Gui {

    private ArrayList<GuiComponent> components = new ArrayList<>();

    public void show(){
        for(GuiComponent c:components)
            c.setShowing(true);
    }

    public void hide(){
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

    public boolean onClick(Vector2f cursorPos) {
        return false;
    }
}
