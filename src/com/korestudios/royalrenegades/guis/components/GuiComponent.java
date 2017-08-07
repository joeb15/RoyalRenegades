package com.korestudios.royalrenegades.guis.components;

import java.util.ArrayList;

public class GuiComponent {

    private boolean show;

    private ArrayList<GuiComponent> children = new ArrayList<>();

    GuiComponent(boolean show){
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

    void addChild(GuiComponent component){
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
