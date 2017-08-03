package com.korestudios.royalrenegades.renderer;

import com.korestudios.royalrenegades.constants.DepthConstants;
import com.korestudios.royalrenegades.font.BitmapFont;
import com.korestudios.royalrenegades.graphics.Texture;
import com.korestudios.royalrenegades.guis.Gui;
import com.korestudios.royalrenegades.guis.GuiManager;
import com.korestudios.royalrenegades.guis.TextComponent;
import com.korestudios.royalrenegades.shaders.Shader;
import com.korestudios.royalrenegades.utils.BitmapData;
import com.korestudios.royalrenegades.utils.RenderData;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiRenderer {

    private Shader shader;

    private GuiManager guiManager;

    private HashMap<BitmapFont, HashMap<Character, ArrayList<RenderData>>[]> textRenderData = new HashMap<>();
    private HashMap<BitmapFont, int[]> charsToDrawHash = new HashMap<>();

    public GuiRenderer(GuiManager guiManager){
        this.guiManager = guiManager;
        shader = new Shader("shaders/font.vert", "shaders/font.frag");
    }

    public void render(){
        clear();
        process();
        renderText();
    }

    private void clear(){
        for(BitmapFont font:textRenderData.keySet()) {
            HashMap<Character, ArrayList<RenderData>>[] renderData = textRenderData.get(font);
            for (int i = 0; i < font.getPages(); i++)
                renderData[i].clear();
        }
    }

    private void process(){
        ArrayList<Gui> guis = guiManager.getGuis();
        for(Gui g:guis){
            processGui(g);
        }
    }

    private void processGui(Gui g) {
        ArrayList<TextComponent> textComponents = g.getTextComponents();
        for(TextComponent textComponent:textComponents){
            processTextComponent(textComponent);
        }
    }

    private void processTextComponent(TextComponent textComponent) {
        if(!textComponent.isShowing())
            return;
        BitmapFont font = textComponent.getFont();
        primeString(font, textComponent.getText(), textComponent.getX(), textComponent.getY(), textComponent.getSize());
    }

    private void primeString(BitmapFont font, String text, float x, float y, float size){
        if(!charsToDrawHash.containsKey(font)){
            int[] charsToDraw = new int[font.getPages()];
            charsToDrawHash.put(font, charsToDraw);
        }
        if(!textRenderData.containsKey(font)){
            HashMap<Character, ArrayList<RenderData>>[] hashMap = new HashMap[font.getPages()];
            for(int i=0;i<font.getPages();i++)
                hashMap[i] = new HashMap<>();
            textRenderData.put(font, hashMap);
        }
        String[] lines = text.split("\n");
        text=lines[0];
        for(int i=1;i<lines.length;i++)
            primeString(font, lines[i], x, y+size*i, size);
        char[] chars = text.toCharArray();
        float scale = size/font.getFontSize();

        HashMap<Character, ArrayList<RenderData>>[] renderData = textRenderData.get(font);
        int[] charsToDraw = charsToDrawHash.get(font);

        for(int i=0;i<chars.length;i++){
            BitmapData data = font.getCharData(chars[i]);
            if(data == null)
                data = font.getCharData((char)0);

            if(!renderData[data.page].containsKey(chars[i]))
                renderData[data.page].put(chars[i], new ArrayList<RenderData>());

            //CREATING RENDERDATA FOR GIVEN CHARACTER
            RenderData rd = new RenderData();
            rd.pos = new Vector2f(x+data.offset.x*scale,y+data.offset.y*scale);
            rd.size = new Vector2f(data.size.x*scale,data.size.y*scale);
            rd.uvpos = new Vector2f(data.pos.x/font.getWidth(), data.pos.y/font.getHeight());
            rd.uvsize = new Vector2f(data.size.x/font.getWidth(), data.size.y/font.getHeight());
            renderData[data.page].get(chars[i]).add(rd);
            charsToDraw[data.page]++;
            //PROCESS KERNINGS AND ADVANCES
            if(i!=0){
                Integer integer = data.kernings.get(chars[i-1]);
                if(integer!=null){
                    x+=integer*scale;
                }
            }
            x+=data.advance*scale;
        }
    }

    private void renderText(){
        shader.setUniform1f("depth", DepthConstants.GUI_TEXT_DEPTH);
        OUTLINE(1,1,1,0,0,0);
        for(BitmapFont font:textRenderData.keySet()) {
            Texture[] fontTextures = font.getFontTextures();
            int[] charsToDraw = charsToDrawHash.get(font);
            HashMap<Character, ArrayList<RenderData>>[] renderData = textRenderData.get(font);

            for(int i=0;i<font.getPages();i++) {
                fontTextures[i].bind(0);
                shader.prime(charsToDraw[i]);
                for (char c : renderData[i].keySet()) {
                    ArrayList<RenderData> data = renderData[i].get(c);
                    for (RenderData rd : data) {
                        shader.load(
                                rd.pos.x,
                                rd.pos.y,
                                rd.size.x,
                                rd.size.y,
                                rd.uvpos.x,
                                rd.uvpos.y,
                                rd.uvsize.x,
                                rd.uvsize.y);
                    }
                }
                shader.draw(charsToDraw[i]);
                charsToDraw[i]=0;
                Texture.unbind();
            }
        }
    }

    private void OUTLINE(float r1, float g1, float b1, float r2, float g2, float b2) {
        shader.setUniform1f("width", .5f);
        shader.setUniform1f("fade", .1f);
        shader.setUniform3f("color", r1,g1,b1);
        shader.setUniform1f("width2", .5f);
        shader.setUniform1f("fade2", .2f);
        shader.setUniform3f("color2", r2,g2,b2);
        shader.setUniform2f("offset", 0,0);
    }

    private void DROPSHADOW(float r1, float g1, float b1, float r2, float g2, float b2, float x, float y) {
        shader.setUniform1f("width", .5f);
        shader.setUniform1f("fade", .1f);
        shader.setUniform3f("color", r1,g1,b1);
        shader.setUniform1f("width2", .5f);
        shader.setUniform1f("fade2", .1f);
        shader.setUniform3f("color2", r2,g2,b2);
        shader.setUniform2f("offset", x, y);
    }

    private void GLOW(float r1, float g1, float b1, float r2, float g2, float b2) {
        shader.setUniform1f("width", .5f);
        shader.setUniform1f("fade", .1f);
        shader.setUniform3f("color", r1,g1,b1);
        shader.setUniform1f("width2", .3f);
        shader.setUniform1f("fade2", .6f);
        shader.setUniform3f("color2", r2,g2,b2);
        shader.setUniform2f("offset", 0,0);
    }

    private Vector2f getSize(BitmapFont font, String text, float size){
        String[] parts = text.split("\n");
        float maxW=0;
        float h = parts.length * size;
        for(int j=0;j<parts.length;j++) {
            char[] chars = text.toCharArray();
            float scale = size / font.getFontSize();
            float x = 0;
            for (int i = 0; i < chars.length; i++) {
                BitmapData data = font.getCharData(chars[i]);
                if (data == null)
                    data = font.getCharData((char) 0);
                if (i != 0) {
                    Integer integer = data.kernings.get(chars[i - 1]);
                    if (integer != null) {
                        x += integer * scale;
                    }
                }
                x += data.advance * scale;
            }
            if(x>maxW)maxW=x;
        }
        return new Vector2f(maxW, h);
    }

}
