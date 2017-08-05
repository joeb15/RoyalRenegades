package com.korestudios.royalrenegades.renderer;

import com.korestudios.royalrenegades.constants.DepthConstants;
import com.korestudios.royalrenegades.font.BitmapFont;
import com.korestudios.royalrenegades.graphics.Texture;
import com.korestudios.royalrenegades.guis.Gui;
import com.korestudios.royalrenegades.guis.GuiManager;
import com.korestudios.royalrenegades.guis.components.BackgroundComponent;
import com.korestudios.royalrenegades.guis.components.GuiComponent;
import com.korestudios.royalrenegades.guis.components.TextComponent;
import com.korestudios.royalrenegades.shaders.Shader;
import com.korestudios.royalrenegades.utils.BitmapData;
import com.korestudios.royalrenegades.utils.RenderData;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiRenderer {

    private Shader fontShader, guiShader;

    private HashMap<BitmapFont, HashMap<Character, ArrayList<RenderData>>[]> textRenderData = new HashMap<>();
    private HashMap<BitmapFont, int[]> charsToDrawHash = new HashMap<>();

    private ArrayList<BackgroundComponent> backgroundComponents = new ArrayList<>();

    public GuiRenderer(){
        fontShader = new Shader("shaders/font.vert", "shaders/font.frag", 4, 4);
        guiShader = new Shader("shaders/gui.vert", "shaders/gui.frag", 4, 4).doesntUseTexture();
    }

    public void render(){
        clear();
        process();
        renderBackgrounds();
        renderText();
    }

    private void clear(){
        for(BitmapFont font:textRenderData.keySet()) {
            HashMap<Character, ArrayList<RenderData>>[] renderData = textRenderData.get(font);
            for (int i = 0; i < font.getPages(); i++)
                renderData[i].clear();
        }
        backgroundComponents.clear();
    }

    private void process(){
        ArrayList<Gui> guis = GuiManager.getGuis();
        for(Gui g:guis){
            ArrayList<GuiComponent> guiComponents = g.getComponents();
            for(GuiComponent guiComponent:guiComponents){
                processComponent(guiComponent);
            }
        }
    }

    private void processComponent(GuiComponent guiComponent){
        if(guiComponent instanceof TextComponent)
            processTextComponent((TextComponent) guiComponent);
        if(guiComponent instanceof BackgroundComponent)
            processBackgroundComponent((BackgroundComponent) guiComponent);
        for(GuiComponent gc:guiComponent.getChildren())
            processComponent(gc);
    }

    private void processBackgroundComponent(BackgroundComponent backgroundComponent){
        if(!backgroundComponent.isShowing())
            return;
        backgroundComponents.add(backgroundComponent);
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
                continue;
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
        fontShader.enable();
        fontShader.setUniform1f("depth", DepthConstants.GUI_TEXT_DEPTH);
        OUTLINE(1,1,1,0,0,0);
        for(BitmapFont font:textRenderData.keySet()) {
            Texture[] fontTextures = font.getFontTextures();
            int[] charsToDraw = charsToDrawHash.get(font);
            HashMap<Character, ArrayList<RenderData>>[] renderData = textRenderData.get(font);

            for(int i=0;i<font.getPages();i++) {
                fontTextures[i].bind(0);
                fontShader.prime(charsToDraw[i]);
                for (char c : renderData[i].keySet()) {
                    ArrayList<RenderData> data = renderData[i].get(c);
                    for (RenderData rd : data) {
                        fontShader.load(
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
                fontShader.draw(charsToDraw[i]);
                charsToDraw[i]=0;
                Texture.unbind();
            }
        }
        fontShader.disable();
    }

    private void renderBackgrounds(){
        guiShader.enable();
        guiShader.setUniform1f("depth", DepthConstants.GUI_DEPTH);
        guiShader.prime(backgroundComponents.size());
        for(BackgroundComponent backgroundComponent:backgroundComponents){
            Vector4f color = backgroundComponent.getColor();
            guiShader.load(
                    backgroundComponent.getX(),
                    backgroundComponent.getY(),
                    backgroundComponent.getW(),
                    backgroundComponent.getH(),
                    color.x,
                    color.y,
                    color.z,
                    color.w
            );
        }
        guiShader.draw(backgroundComponents.size());
        guiShader.disable();
    }

    private void OUTLINE(float r1, float g1, float b1, float r2, float g2, float b2) {
        fontShader.setUniform1f("width", .5f);
        fontShader.setUniform1f("fade", .1f);
        fontShader.setUniform3f("color", r1,g1,b1);
        fontShader.setUniform1f("width2", .5f);
        fontShader.setUniform1f("fade2", .2f);
        fontShader.setUniform3f("color2", r2,g2,b2);
        fontShader.setUniform2f("offset", 0,0);
    }

    private void DROPSHADOW(float r1, float g1, float b1, float r2, float g2, float b2, float x, float y) {
        fontShader.setUniform1f("width", .5f);
        fontShader.setUniform1f("fade", .1f);
        fontShader.setUniform3f("color", r1,g1,b1);
        fontShader.setUniform1f("width2", .5f);
        fontShader.setUniform1f("fade2", .1f);
        fontShader.setUniform3f("color2", r2,g2,b2);
        fontShader.setUniform2f("offset", x, y);
    }

    private void GLOW(float r1, float g1, float b1, float r2, float g2, float b2) {
        fontShader.setUniform1f("width", .5f);
        fontShader.setUniform1f("fade", .1f);
        fontShader.setUniform3f("color", r1,g1,b1);
        fontShader.setUniform1f("width2", .3f);
        fontShader.setUniform1f("fade2", .6f);
        fontShader.setUniform3f("color2", r2,g2,b2);
        fontShader.setUniform2f("offset", 0,0);
    }
}
