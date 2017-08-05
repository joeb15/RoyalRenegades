package com.korestudios.royalrenegades.renderer;

import com.korestudios.royalrenegades.world.World;

public class MasterRenderer {

    private ForegroundRenderer foregroundRenderer;
    private EntityRenderer entityRenderer;
    private GuiRenderer guiRenderer;

    public MasterRenderer(World world){
        foregroundRenderer = new ForegroundRenderer(world);
        entityRenderer = new EntityRenderer(world);
        guiRenderer = new GuiRenderer();
    }

    public void render(){
        foregroundRenderer.render();
        entityRenderer.render();
        guiRenderer.render();
    }

}
