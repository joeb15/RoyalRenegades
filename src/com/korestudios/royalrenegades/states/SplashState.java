package com.korestudios.royalrenegades.states;

import com.korestudios.royalrenegades.guis.GuiManager;
import com.korestudios.royalrenegades.guis.SplashGui;
import com.korestudios.royalrenegades.renderer.GuiRenderer;
import com.korestudios.royalrenegades.sound.Sound;
import com.korestudios.royalrenegades.sound.Source;

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

        Source source = new Source(0,0,0);
        source.play(Sound.getSound("hello.wav"));

        GuiManager.addGui(new SplashGui());
    }

    @Override
    public void destroy() {

    }
}
