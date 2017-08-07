package com.korestudios.royalrenegades.states;

import com.korestudios.royalrenegades.guis.DebugGui;
import com.korestudios.royalrenegades.guis.GuiManager;
import com.korestudios.royalrenegades.guis.SettingGui;
import com.korestudios.royalrenegades.renderer.MasterRenderer;
import com.korestudios.royalrenegades.world.World;

public class GameState extends State {

    private World world;
    private MasterRenderer masterRenderer;

    @Override
    public void update() {
        world.update();
    }

    @Override
    public void render() {
        masterRenderer.render();
    }

    @Override
    public void init() {
        world = new World();
        masterRenderer = new MasterRenderer(world);

        GuiManager.addGui(new DebugGui());
        GuiManager.addGui(new SettingGui());
    }

    @Override
    public void destroy() {

    }
}
