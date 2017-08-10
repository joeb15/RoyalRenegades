package com.korestudios.royalrenegades.guis.components;

import com.korestudios.royalrenegades.graphics.Image;

public class ImageComponent extends GuiComponent{

    private Image image;
    private TextInterface imagePath = null;
    private float x, y, w, h;
    private String lastImage = "";
    public ImageComponent(TextInterface imagePath, float x, float y, float w, float h, boolean show){
        super(show);
        this.imagePath=imagePath;
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
    }

    public ImageComponent(String imagePath, float x, float y, float w, float h, boolean show){
        this(new Image(imagePath, x,y,w,h),show);
    }

    public ImageComponent(Image image, boolean show){
        super(show);
        this.image = image;
    }

    public Image getImage(){
        String currImage;
        if(imagePath!=null && !(currImage=imagePath.getText()).equals(lastImage)){
            lastImage=currImage;
            return image=new Image(currImage, x, y, w, h);
        }
        return image;
    }

}
