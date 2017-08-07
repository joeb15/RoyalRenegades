package com.korestudios.royalrenegades.guis.components;

import org.joml.Vector2f;

import java.util.ArrayList;

public class GuiComponent {

    private boolean show;

    private ArrayList<GuiComponent> children = new ArrayList<>();

    public GuiComponent(boolean show){
        this.show=show;
    }

    public boolean isShowing(){
        return show;
    }
    public void setShowing(boolean showing){
        this.show=showing;
        for(GuiComponent c:children)
            c.setShowing(showing);
    }
    public void addChild(GuiComponent component){
        children.add(component);
    }

    public ArrayList<GuiComponent> getChildren() {
        return children;
    }

    public void update() {
        for(GuiComponent c:children)
            c.update();
    }
}
