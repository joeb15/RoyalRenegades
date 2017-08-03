package com.korestudios.royalrenegades.input;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by joe on 7/13/17.
 */
public class Input {

    private static long window = -1L;

    public static void init(long window){
        if(Input.window==-1)
            Input.window=window;
    }

    public static boolean isKeyDown(int key){
        return glfwGetKey(window, key)==GLFW_PRESS;
    }

    public static boolean isMouseButtonDown(int button){
        return glfwGetMouseButton(window, button)==GLFW_PRESS;
    }

    public static Vector2f getCursorPos(){
        double[] x = new double[1];
        double[] y = new double[1];
        glfwGetCursorPos(window, x, y);
        return new Vector2f((float)x[0], (float)y[0]);
    }

}
