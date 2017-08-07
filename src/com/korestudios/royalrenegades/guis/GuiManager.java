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

    public static void update() {
        for(Gui g:guis)
            g.update();
    }
}
