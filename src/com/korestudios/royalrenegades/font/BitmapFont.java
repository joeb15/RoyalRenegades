package com.korestudios.royalrenegades.font;

import com.korestudios.royalrenegades.graphics.Texture;
import com.korestudios.royalrenegades.utils.BitmapData;
import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;
import org.joml.Vector2f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import static com.korestudios.royalrenegades.constants.ErrorConstants.FONT_NOT_FOUND_ERROR;
import static com.korestudios.royalrenegades.constants.ErrorConstants.UNEXPECTED_FONT_FILE;
import static org.lwjgl.opengl.GL11.GL_LINEAR;

public class BitmapFont {

    public static final BitmapFont FONT_DEJAVU = new BitmapFont("res/fonts/dejavu/Dejavu.fnt");
    public static final BitmapFont FONT_COURIER = new BitmapFont("res/fonts/courier/Courier.fnt");

    private HashMap<Character, BitmapData> charData = new HashMap<>();

    private int width, height, fontSize, pages;

    private Texture[] fontTextures;

    private int getAfterEquals(String part){
        String[] parts = part.split("=");
        if(parts.length!=2) {
            System.err.println(part);
            return -1;
        }
        return Integer.parseInt(parts[1]);
    }

    public BitmapFont(String fontFile){
        try {
            String path = fontFile.substring(0, fontFile.lastIndexOf("/"));
            BufferedReader br = new BufferedReader(new FileReader(fontFile));
            String line;
            int chars=0, kernings=0, expectedChars=-1, expectedKernings=-1;
            while((line = br.readLine())!=null){
                line = line.replaceAll("\\s+", " ");
                String[] parts = line.split(" ");
                if(parts[0].equals("page")){
                    int currpage = getAfterEquals(parts[1]);
                    String file = path + "/" + parts[2].split("=")[1].replace("\"","");
                    fontTextures[currpage] = new Texture(file, GL_LINEAR);
                }else if(parts[0].equals("char")){
                    int id = getAfterEquals(parts[1]);
                    int x = getAfterEquals(parts[2]);
                    int y = getAfterEquals(parts[3]);
                    int width = getAfterEquals(parts[4]);
                    int height = getAfterEquals(parts[5]);
                    int xOff = getAfterEquals(parts[6]);
                    int yOff = getAfterEquals(parts[7]);
                    int adv = getAfterEquals(parts[8]);
                    int page = getAfterEquals(parts[9]);
                    charData.put((char)id, new BitmapData(x, y, width, height, xOff, yOff, adv, page));
                    chars++;
                }else if(parts[0].equals("kerning")){
                    char c = (char)getAfterEquals(parts[2]);
                    charData.get(c).kernings.put((char)getAfterEquals(parts[1]), getAfterEquals(parts[3]));
                    kernings++;
                }else if(parts[0].equals("kernings")){
                    expectedKernings = getAfterEquals(parts[1]);
                }else if(parts[0].equals("chars")){
                    expectedChars = getAfterEquals(parts[1]);
                }else if(parts[0].equals("common")){
                    fontSize = getAfterEquals(parts[1]);
                    width = getAfterEquals(parts[3]);
                    height = getAfterEquals(parts[4]);
                    pages = getAfterEquals(parts[5]);
                    fontTextures = new Texture[pages];
                }
            }
            if(chars!=expectedChars){
                Logger.log(PRIORITY.ERRORS, "BitmapFont", "Character count: "+chars+" was not equal to expected: "+expectedChars, UNEXPECTED_FONT_FILE, false);
            }
            if(kernings!=expectedKernings){
                Logger.log(PRIORITY.ERRORS, "BitmapFont", "Kerning count: "+kernings+" was not equal to expected: "+expectedKernings, UNEXPECTED_FONT_FILE, false);
            }
        } catch (IOException e) {
            Logger.log(PRIORITY.CRITICAL_ERRORS, "BitmapFont", "Unable to createChunk font file: "+fontFile+"\n"+e.getMessage(), FONT_NOT_FOUND_ERROR, true);
        }
    }

    public Vector2f getStringSize(String text, float size){
        return new Vector2f(getStringWidth(text, size), getStringHeight(text, size));
    }

    public float getStringWidth(String text, float size){
        String[] parts = text.split("\n");
        float maxW=0;
        for(int j=0;j<parts.length;j++) {
            char[] chars = parts[j].toCharArray();
            float scale = size / getFontSize();
            float x = 0;
            for (int i = 0; i < chars.length; i++) {
                BitmapData data = getCharData(chars[i]);
                if (data == null)
                    continue;
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
        return maxW;
    }

    public float getStringHeight(String text, float size){
        String[] parts = text.split("\n");
        return parts.length * size;
    }

    public int getFontSize() {
        return fontSize;
    }

    public BitmapData getCharData(char c) {
        return charData.get(c);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPages() {
        return pages;
    }

    public Texture[] getFontTextures() {
        return fontTextures;
    }
}
