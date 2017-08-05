package com.korestudios.royalrenegades.guis.components;

import com.korestudios.royalrenegades.font.BitmapFont;
import com.korestudios.royalrenegades.input.Input;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class ButtonComponent extends GuiComponent{

    private String lastText = "";
    private TextInterface textInterface;
    private float x,y,w,h,fontSize;
    private BitmapFont font;
    private TextComponent textComponent;
    private boolean triggered=false;

    private ButtonInterface buttonInterface;

    public ButtonComponent(float x, float y, float w, float h, float border, float fontSize, ButtonInterface buttonInterface, ColorInterface backgroundCol,
                           ColorInterface borderCol, TextInterface textInterface, BitmapFont font, boolean show) {
        super(show);

        this.buttonInterface=buttonInterface;
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.textInterface = textInterface;
        this.font=font;
        this.fontSize=fontSize;

        BackgroundComponent bc = new BackgroundComponent(backgroundCol, x+border,y+border,w-border*2,h-border*2, show);
        BackgroundBorderComponent bbc = new BackgroundBorderComponent(bc, borderCol, border, show);
        float textWidth = font.getStringWidth(textInterface.getText(), fontSize);
        float textHeight = font.getStringHeight(textInterface.getText(), fontSize);
        TextComponent tc = new TextComponent(textInterface,font, x+w/2-textWidth/2,y+h/2-textHeight/2,fontSize, show);

        this.textComponent = tc;

        addChild(bc);
        addChild(bbc);
        addChild(tc);
    }

    public void update(){
        String currText = textInterface.getText();
        if(!currText.equals(lastText)){
            lastText=currText;
            float textWidth = font.getStringWidth(textInterface.getText(), fontSize);
            float textHeight = font.getStringHeight(textInterface.getText(), fontSize);
            textComponent.setX(x+w/2-textWidth/2);
            textComponent.setY(y+h/2-textHeight/2);
        }
        if(triggered && !Input.isMouseButtonDown(GLFW_MOUSE_BUTTON_1)) {
            triggered = false;
            buttonInterface.onClick();
        }
    }

    public boolean onClick(Vector2f pos){
        boolean inRect = pos.x>x && pos.x<x+w && pos.y>y && pos.y<y+h;
        if(!triggered && inRect){
            triggered=true;
            return true;
        }else if(!inRect){
            triggered=false;
        }
        return false;
    }

    public boolean isTriggered() {
        return triggered;
    }
}
