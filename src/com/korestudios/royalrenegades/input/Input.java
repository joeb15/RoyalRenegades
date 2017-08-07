package com.korestudios.royalrenegades.input;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by joe on 7/13/17.
 */
public class Input {

    private static long window = -1L;

    private static HashMap<Integer, InputListenersData> inputListenersData = new HashMap<>();

    public static final int TYPE_KEY_UP= 0;
    public static final int TYPE_KEY_DOWN = 1;
    public static final int TYPE_KEY_PRESS = 2;
    public static final int TYPE_KEY_RELEASE = 3;
    public static final int TYPE_MOUSE_UP = 4;
    public static final int TYPE_MOUSE_DOWN = 5;
    public static final int TYPE_MOUSE_PRESS = 6;
    public static final int TYPE_MOUSE_RELEASE = 7;

    public static void init(long window){
        if(Input.window==-1)
            Input.window=window;
    }

    public static void update(){
        for(InputListenersData data:inputListenersData.values())
            data.update();
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

    public static void addListener(int key, int listenerType, InputListener inputListener) {
        if(!inputListenersData.containsKey(key))
            inputListenersData.put(key, new InputListenersData(key));
        InputListenersData data = inputListenersData.get(key);
        switch (listenerType){
            case TYPE_KEY_UP:data.addKeyUpListener(inputListener);return;
            case TYPE_KEY_DOWN:data.addKeyDownListener(inputListener);return;
            case TYPE_KEY_PRESS:data.addKeyPressListener(inputListener);return;
            case TYPE_KEY_RELEASE:data.addKeyReleaseListener(inputListener);return;
            case TYPE_MOUSE_UP:data.addMouseUpListener(inputListener);return;
            case TYPE_MOUSE_DOWN:data.addMouseDownListener(inputListener);return;
            case TYPE_MOUSE_PRESS:data.addMousePressListener(inputListener);return;
            case TYPE_MOUSE_RELEASE:data.addMouseReleaseListener(inputListener);return;
        }
    }

    private static class InputListenersData{
        private ArrayList<InputListener> keyUpListeners = new ArrayList<>();
        private ArrayList<InputListener> keyDownListeners = new ArrayList<>();
        private ArrayList<InputListener> keyPressListeners = new ArrayList<>();
        private ArrayList<InputListener> keyReleaseListeners = new ArrayList<>();

        private ArrayList<InputListener> mouseUpListeners = new ArrayList<>();
        private ArrayList<InputListener> mouseDownListeners = new ArrayList<>();
        private ArrayList<InputListener> mousePressListeners = new ArrayList<>();
        private ArrayList<InputListener> mouseReleaseListeners = new ArrayList<>();
        private boolean wasDown = false;
        private boolean wasMouseDown = false;
        private int key;

        public InputListenersData(int key) {
            this.key=key;
        }

        private void update(){
            boolean isDown = isKeyDown(key);
            boolean isMouseDown = isMouseButtonDown(key);

            if(isDown && !wasDown)for(InputListener il:keyPressListeners)il.onAction();
            if(!isDown && wasDown)for(InputListener il:keyReleaseListeners)il.onAction();
            if(isDown)for(InputListener il:keyDownListeners)il.onAction();
            if(!isDown)for(InputListener il:keyUpListeners)il.onAction();

            if(isMouseDown && !wasMouseDown)for(InputListener il:mousePressListeners)il.onAction();
            if(!isMouseDown && wasMouseDown)for(InputListener il:mouseReleaseListeners)il.onAction();
            if(isMouseDown)for(InputListener il:mouseDownListeners)il.onAction();
            if(!isMouseDown)for(InputListener il:mouseUpListeners)il.onAction();

            wasDown=isDown;
            wasMouseDown=isMouseDown;
        }

        private void addMouseUpListener(InputListener il){mouseUpListeners.add(il);}
        private void addMouseDownListener(InputListener il){mouseDownListeners.add(il);}
        private void addMouseReleaseListener(InputListener il){mouseReleaseListeners.add(il);}
        private void addMousePressListener(InputListener il){mousePressListeners.add(il);}
        private void addKeyUpListener(InputListener il){keyUpListeners.add(il);}
        private void addKeyDownListener(InputListener il){keyDownListeners.add(il);}
        private void addKeyReleaseListener(InputListener il){keyReleaseListeners.add(il);}
        private void addKeyPressListener(InputListener il){keyPressListeners.add(il);}
    }
}
