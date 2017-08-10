package com.korestudios.royalrenegades.states;

import com.korestudios.royalrenegades.guis.DebugGui;
import com.korestudios.royalrenegades.guis.GuiManager;
import com.korestudios.royalrenegades.guis.SettingGui;
import com.korestudios.royalrenegades.input.Input;
import com.korestudios.royalrenegades.renderer.EntityRenderer;
import com.korestudios.royalrenegades.renderer.ForegroundRenderer;
import com.korestudios.royalrenegades.renderer.GuiRenderer;
import com.korestudios.royalrenegades.world.World;

import static com.korestudios.royalrenegades.constants.GlobalVariables.PROJECTION_MATRIX;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;

public class GameState extends State {

    private World world;
    private ForegroundRenderer foregroundRenderer;
    private EntityRenderer entityRenderer;
    private GuiRenderer guiRenderer;

    @Override
    public void update() {
        world.update();
    }

    @Override
    public void render() {
        foregroundRenderer.render();
        entityRenderer.render();
        guiRenderer.render();
    }

    @Override
    public void init() {
        world = new World();
        foregroundRenderer = new ForegroundRenderer(world);
        entityRenderer = new EntityRenderer(world);
        guiRenderer = new GuiRenderer();

        GuiManager.addGui(new DebugGui());
        GuiManager.addGui(new SettingGui());
    }

    @Override
    public void destroy() {

    }
}
