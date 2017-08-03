package com.korestudios.royalrenegades.utils;

import com.korestudios.royalrenegades.constants.ErrorConstants;
import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;
import org.lwjgl.glfw.GLFWErrorCallbackI;

import java.io.PrintStream;

import static org.lwjgl.glfw.GLFWErrorCallback.getDescription;

/**
 * Created by joe on 7/13/17.
 */
public class ErrorCallback extends PrintStream implements GLFWErrorCallbackI {
    public ErrorCallback() {
        super(System.err);
    }

    private StringBuilder outputStorage;
    private PRIORITY currPriority = PRIORITY.VERBOSE;

    @Override
    public void println(String s){
        print(s);
    }

    @Override
    public void print(String s){
        if(s.equals("[LWJGL] OpenGL debug message")) {
            outputStorage = new StringBuilder();
            outputStorage.append(s);
            currPriority=PRIORITY.ERRORS;
        }else if(s.contains("NOTIFICATION")) {
            currPriority=PRIORITY.VERBOSE;
        }else if(s.endsWith(".")){
            outputStorage.append(s);
            if(currPriority==PRIORITY.ERRORS)
                Logger.log(currPriority, "LWJGL", outputStorage.toString(), ErrorConstants.LWJGL_GENERIC_ERROR, false);
            else
                Logger.log(currPriority, "LWJGL", outputStorage.toString());
        }else{
            outputStorage.append(s);
        }
    }

    @Override
    public String getSignature() {
        return null;
    }

    @Override
    public long address() {
        return 0;
    }

    @Override
    public void callback(long args) {}

    @Override
    public void invoke(int error, long description) {
        String desc = getDescription(description);
        if(error<=0)
            return;
        Logger.log(PRIORITY.ERRORS, "LWJGL", desc, error, true);
    }
}
