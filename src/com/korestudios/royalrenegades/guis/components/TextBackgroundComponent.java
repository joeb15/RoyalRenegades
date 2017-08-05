package com.korestudios.royalrenegades.guis.components;

public class TextBackgroundComponent extends BackgroundComponent {

    private TextComponent textComponent;
    private float xShift, yShift, widthShift, heightShift;

    public TextBackgroundComponent(TextComponent textComponent, ColorInterface colorInterface, boolean show) {
        this(textComponent, colorInterface, 0,0,0,0, show);
    }

    public TextBackgroundComponent(TextComponent textComponent, ColorInterface colorInterface,
                                   float xShift, float yShift, float widthShift, float heightShift, boolean show) {
        super(colorInterface, textComponent.getX(), textComponent.getY(), 0, 0, show);
        this.textComponent = textComponent;
        this.xShift=xShift;
        this.yShift=yShift;
        this.widthShift=widthShift;
        this.heightShift=heightShift;
    }

    public float getX(){
        return textComponent.getX()+xShift;
    }

    public float getY(){
        return textComponent.getY()+yShift;
    }

    public float getW(){
        return textComponent.getFont().getStringWidth(textComponent.getText(), textComponent.getSize())+widthShift;
    }

    public float getH(){
        return textComponent.getFont().getStringHeight(textComponent.getText(), textComponent.getSize())+heightShift;
    }
}
