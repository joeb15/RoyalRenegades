package com.korestudios.royalrenegades.guis;

import com.korestudios.royalrenegades.input.Input;
import org.joml.Vector2f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class GuiManager {

    private static ArrayList<Gui> guis = new ArrayList<Gui>();;

    public static void addGui(Gui gui){
        guis.add(gui);
    }

    public static ArrayList<Gui> getGuis() {
        return guis;
    }

    public static void onClick(Vector2f cursorPos) {
        for(Gui g:guis)
            if(g.onClick(cursorPos))
                return;
    }

    public static void update() {
        if(Input.isMouseButtonDown(GLFW_MOUSE_BUTTON_1))
            onClick(Input.getCursorPos());
        for(Gui g:guis)
            g.update();
    }
}
