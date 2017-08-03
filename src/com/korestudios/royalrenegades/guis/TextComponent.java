package com.korestudios.royalrenegades.guis;

import com.korestudios.royalrenegades.font.BitmapFont;
import com.korestudios.royalrenegades.utils.BitmapData;
import org.joml.Vector2f;

public class TextComponent {

    private TextInterface text;
    private BitmapFont font;
    private float x, y, size;
    private boolean show;

    public TextComponent(TextInterface text, BitmapFont font, float x, float y, float size, boolean show) {
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
        this.size=size;
        this.show = show;
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

    public boolean isShowing() {
        return show;
    }

}
