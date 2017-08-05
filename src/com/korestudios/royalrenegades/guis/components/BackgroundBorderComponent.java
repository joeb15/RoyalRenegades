package com.korestudios.royalrenegades.guis.components;

public class BackgroundBorderComponent extends BackgroundComponent {

    private BackgroundComponent backgroundComponent;
    private float xShift, yShift, widthShift, heightShift;

    public BackgroundBorderComponent(BackgroundComponent backgroundComponent, ColorInterface colorInterface, float width, boolean show) {
        this(backgroundComponent, colorInterface, -width, -width, width*2, width*2, show);
    }

    public BackgroundBorderComponent(BackgroundComponent backgroundComponent, ColorInterface colorInterface,
                                     float xShift, float yShift, float widthShift, float heightShift, boolean show) {
        super(colorInterface, backgroundComponent.getX(), backgroundComponent.getY(), 0, 0, show);
        this.backgroundComponent = backgroundComponent;
        this.xShift=xShift;
        this.yShift=yShift;
        this.widthShift=widthShift;
        this.heightShift=heightShift;
    }

    public float getX(){
        return backgroundComponent.getX()+xShift;
    }

    public float getY(){
        return backgroundComponent.getY()+yShift;
    }

    public float getW(){
        return backgroundComponent.getW()+widthShift;
    }

    public float getH(){
        return backgroundComponent.getH()+heightShift;
    }
}
