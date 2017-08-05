package com.korestudios.royalrenegades.guis.components;

public class GuiComponent {

    private boolean show;

    private ClickInterface clickInterface;

    public GuiComponent(boolean show){
        this.show=show;
    }

    public boolean isShowing(){
        return show;
    }
    public void setShowing(boolean showing){
        this.show=showing;
    }

    public boolean contains(float x, float y){
        if(clickInterface==null)
            return false;
        return clickInterface.onClick(x, y);
    }

    public void setClickInterface(ClickInterface clickInterface){
        this.clickInterface=clickInterface;
    }
}
