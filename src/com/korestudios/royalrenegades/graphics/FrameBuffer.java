package com.korestudios.royalrenegades.graphics;

import com.korestudios.royalrenegades.constants.ErrorConstants;
import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import static com.korestudios.royalrenegades.constants.ErrorConstants.*;
import static com.korestudios.royalrenegades.constants.GlobalVariables.FRAME_HEIGHT;
import static com.korestudios.royalrenegades.constants.GlobalVariables.FRAME_WIDTH;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;
import static org.lwjgl.opengles.GLES20.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS;

public class FrameBuffer {

    private int fboID, textureID, depthID, w, h;

    private static ArrayList<Integer> fbos = new ArrayList<>();
    private static ArrayList<Integer> textures = new ArrayList<>();
    private static ArrayList<Integer> depths = new ArrayList<>();

    public FrameBuffer(int w, int h){
        this.w=w;
        this.h=h;
        fboID = glGenFramebuffers();
        textureID = glGenTextures();
        depthID = glGenTextures();

        fbos.add(fboID);
        depths.add(depthID);
        textures.add(textureID);

        glBindFramebuffer(GL_FRAMEBUFFER, fboID);
        glDrawBuffer(GL_COLOR_ATTACHMENT0);

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, w, h, 0, GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, textureID, 0);

        glBindTexture(GL_TEXTURE_2D, depthID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT32, w, h, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer)null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthID, 0);

        errorCheck();
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);
        glPushAttrib(GL_VIEWPORT_BIT);
        glViewport(0,0,FRAME_WIDTH, FRAME_HEIGHT);
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    }

    public void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glPopAttrib();
    }

    public void bindTexture(int i){
        if(i<0||i>31){
            Logger.log(PRIORITY.ERRORS, "FrameBuffer", "FrameBuffer texture index '"+i+"' is not within the range [0-31]", ErrorConstants.IMAGE_BIND_OOB_ERROR, false);
        }
        glActiveTexture(GL_TEXTURE0+i);
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void unbindTexture(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void bindDepthTexture(int i){
        if(i<0||i>31){
            Logger.log(PRIORITY.ERRORS, "FrameBuffer", "FrameBuffer texture index '"+i+"' is not within the range [0-31]", ErrorConstants.IMAGE_BIND_OOB_ERROR, false);
        }
        glActiveTexture(GL_TEXTURE0+i);
        glBindTexture(GL_TEXTURE_2D, depthID);
    }

    public void unbindDepthTexture(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static void cleanup(){
        for(int i:textures)
            glDeleteTextures(i);
        for(int i:fbos)
           glDeleteFramebuffers(i);
        for(int i:depths)
            glDeleteRenderbuffers(i);
    }

    private void errorCheck() {
        int framebuffer = glCheckFramebufferStatus(GL_FRAMEBUFFER);
        switch (framebuffer) {
            case GL_FRAMEBUFFER_COMPLETE:
                break;
            case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
                Logger.log(PRIORITY.CRITICAL_ERRORS, "FrameBuffer", "FrameBuffer: " + fboID
                        + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT exception", FRAMEBUFFER_INCOMPLETE, true);
            case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
                Logger.log(PRIORITY.CRITICAL_ERRORS, "FrameBuffer", "FrameBuffer: " + fboID
                        + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT exception", FRAMEBUFFER_MISSING_ATTACHMENT, true);
            case GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS:
                Logger.log(PRIORITY.CRITICAL_ERRORS, "FrameBuffer", "FrameBuffer: " + fboID
                        + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS exception", FRAMEBUFFER_INCOMPLETE_DIM, true);
            case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
                Logger.log(PRIORITY.CRITICAL_ERRORS, "FrameBuffer", "FrameBuffer: " + fboID
                        + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER exception", FRAMEBUFFER_INCOMPLETE_DRAW, true);
            case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
                Logger.log(PRIORITY.CRITICAL_ERRORS, "FrameBuffer", "FrameBuffer: " + fboID
                        + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER exception", FRAMEBUFFER_INCOMPLETE_READ, true);
            default:
                Logger.log(PRIORITY.CRITICAL_ERRORS, "FrameBuffer", "Unexpected reply from glCheckFramebufferStatusEXT: " + framebuffer, FRAMEBUFFER_OTHER_ERROR, true);
        }
    }

}
