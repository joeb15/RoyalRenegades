package com.korestudios.royalrenegades.guis.components;

import com.korestudios.royalrenegades.font.BitmapFont;

public class TextComponent extends GuiComponent {

    private TextInterface text;
    private BitmapFont font;
    private float x, y, size;

    public void setText(TextInterface text) {
        this.text = text;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setSize(float size) {
        this.size = size;
    }


    public TextComponent(TextInterface text, BitmapFont font, float x, float y, float size, boolean show) {
        super(show);
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
        this.size=size;
    }

    public BitmapFont getFont() {
        return font;
    }

    public float getSize() {
        return size;
    }

    public String getText() {
        return text.getText();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
