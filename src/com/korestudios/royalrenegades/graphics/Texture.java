package com.korestudios.royalrenegades.graphics;

import com.korestudios.royalrenegades.constants.ErrorConstants;
import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;
import com.korestudios.royalrenegades.vfs.FileSystem;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static com.korestudios.royalrenegades.constants.ErrorConstants.IMAGE_LOAD_ERROR;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * Created by joe on 7/13/17.
 */
public class Texture {

    private int width, height;
    private int texture;
    public final int identifier;
    public final String path;

    private static int numTexs = 0;

    private static ArrayList<Integer> textures = new ArrayList<Integer>();

    private static HashMap<String, Texture> hashedTextures = new HashMap<String, Texture>();

    public Texture(BufferedImage bi, int GL_MODE){
        this.path="generatedImage";
        identifier=numTexs++;
        texture=load(bi, GL_MODE);
        textures.add(texture);
    }

    public Texture(String path){
        this(path, GL_NEAREST);
    }

    public Texture(String path, int GL_MODE){
        this.path=path;
        identifier = numTexs++;
        texture = load(path, GL_MODE);
        textures.add(texture);
        hashedTextures.put(path, this);
    }

    private int load(String path, int GL_MODE){
        try{
            BufferedImage bi = ImageIO.read(FileSystem.getFile(path));
            return load(bi, GL_MODE);
        }catch (IOException e){
            Logger.log(PRIORITY.CRITICAL_ERRORS, "Texture", e.getMessage(), IMAGE_LOAD_ERROR, true);
        }
        return -1;
    }

    public static Texture getTexture(String path){
        if(!hashedTextures.containsKey(path))
            hashedTextures.put(path, new Texture(path));
        return hashedTextures.get(path);
    }

    private int load(BufferedImage bi, int GL_MODE){
        int[] pixels = null;
        width=bi.getWidth();
        height=bi.getHeight();
        pixels = new int[width*height];
        bi.getRGB(0,0,width,height,pixels,0,width);
        IntBuffer ib = BufferUtils.createIntBuffer(width*height);
        for(int i=0;i<width*height;i++){
            int a = (pixels[i] >> 24) & 0xff;
            int r = (pixels[i] >> 16) & 0xff;
            int g = (pixels[i] >> 8) & 0xff;
            int b = pixels[i] & 0xff;
            ib.put(a<<24 | b << 16 | g << 8 | r);
        }
        ib.flip();

        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_MODE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_MODE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, ib);
        unbind();
        return result;
    }

    public void bind(int i){
        if(i<0||i>31){
            Logger.log(PRIORITY.ERRORS, "Texture", "Texture index '"+i+"' is not within the range [0-31]", ErrorConstants.IMAGE_BIND_OOB_ERROR, false);
        }
        glActiveTexture(GL_TEXTURE0+i);
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public boolean equals(Object o){
        if(!(o instanceof Texture))return false;
        return ((Texture)o).identifier==identifier;
    }

    public static void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static void cleanup(){
        for(Integer i:textures)
            glDeleteTextures(i);
    }

    public String toString(){
        return path;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
