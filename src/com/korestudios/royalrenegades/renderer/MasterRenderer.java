package com.korestudios.royalrenegades.renderer;

import com.korestudios.royalrenegades.constants.DepthConstants;
import com.korestudios.royalrenegades.guis.GuiManager;
import com.korestudios.royalrenegades.world.World;

import static com.korestudios.royalrenegades.constants.VariableConstants.FRAME_HEIGHT;
import static com.korestudios.royalrenegades.constants.VariableConstants.FRAME_WIDTH;

public class MasterRenderer {

    private ForegroundRenderer foregroundRenderer;
    private EntityRenderer entityRenderer;
    private GuiRenderer guiRenderer;

    public MasterRenderer(World world, GuiManager guiManager){
        foregroundRenderer = new ForegroundRenderer(world);
        entityRenderer = new EntityRenderer(world);
        guiRenderer = new GuiRenderer(guiManager);
    }

    public void render(){
        foregroundRenderer.render();
        entityRenderer.render();
        guiRenderer.render();
    }

}
