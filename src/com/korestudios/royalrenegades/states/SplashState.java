package com.korestudios.royalrenegades.states;

import com.korestudios.royalrenegades.guis.GuiManager;
import com.korestudios.royalrenegades.guis.SplashGui;
import com.korestudios.royalrenegades.renderer.GuiRenderer;

public class SplashState extends State {

    private GuiRenderer guiRenderer;

    @Override
    public void update() {

    }

    @Override
    public void render() {
        guiRenderer.render();
    }

    @Override
    public void init() {
        guiRenderer = new GuiRenderer();

        GuiManager.addGui(new SplashGui());
    }

    @Override
    public void destroy() {

    }
}
