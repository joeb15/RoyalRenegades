package com.korestudios.royalrenegades.guis.components;

import com.korestudios.royalrenegades.font.BitmapFont;
import com.korestudios.royalrenegades.input.Input;
import com.korestudios.royalrenegades.input.InputListener;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class ButtonComponent extends GuiComponent{

    private String lastText = "";
    private TextInterface textInterface;
    private float x,y,w,h,fontSize;
    private BitmapFont font;
    private TextComponent textComponent;
    private InputListener inputListener;

    public ButtonComponent(String image, float x, float y, float w, float h, float fontSize,
                           InputListener inputListener, TextInterface textInterface, BitmapFont font, boolean show){
        super(show);
        setAllDefaults(x, y, w, h, inputListener, textInterface, font, fontSize, show);
        addChild(new ImageComponent(image, x, y, w, h, show));
    }

    public ButtonComponent(TextInterface image, float x, float y, float w, float h, float fontSize,
                           InputListener inputListener, TextInterface textInterface, BitmapFont font, boolean show){
        super(show);
        setAllDefaults(x, y, w, h, inputListener, textInterface, font, fontSize, show);
        addChild(new ImageComponent(image, x, y, w, h, show));
    }

    public ButtonComponent(float x, float y, float w, float h, float border, float fontSize, InputListener inputListener, ColorInterface backgroundCol,
                           ColorInterface borderCol, TextInterface textInterface, BitmapFont font, boolean show) {
        super(show);
        setAllDefaults(x, y, w, h, inputListener, textInterface, font, fontSize, show);

        BackgroundComponent bc = new BackgroundComponent(backgroundCol, x+border,y+border,w-border*2,h-border*2, show);
        BackgroundBorderComponent bbc = new BackgroundBorderComponent(bc, borderCol, border, show);


        addChild(bc);
        addChild(bbc);
    }

    private void setAllDefaults(float x, float y, float w, float h, InputListener inputListener,
                                TextInterface textInterface, BitmapFont font, float fontSize, boolean show){
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.inputListener = inputListener;
        this.textInterface = textInterface;
        this.font=font;
        this.fontSize=fontSize;

        float textWidth = font.getStringWidth(textInterface.getText(), fontSize);
        float textHeight = font.getStringHeight(textInterface.getText(), fontSize);
        TextComponent tc = new TextComponent(textInterface,font, x+w/2-textWidth/2,y+h/2-textHeight/2,fontSize, show);

        this.textComponent = tc;
        addChild(tc);
        Input.addListener(GLFW_MOUSE_BUTTON_1, Input.TYPE_MOUSE_RELEASE, ()->{if(isShowing())onClick();});
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
    }

    private boolean onClick(){
        Vector2f pos = Input.getCursorPos();
        boolean inRect = pos.x>x && pos.x<x+w && pos.y>y && pos.y<y+h;
        if(inRect){
            inputListener.onAction();
            return true;
        }
        return false;
    }
}
