package com.korestudios.royalrenegades.renderer;

import com.korestudios.royalrenegades.constants.DepthConstants;
import com.korestudios.royalrenegades.entities.Entity;
import com.korestudios.royalrenegades.shaders.Shader;
import com.korestudios.royalrenegades.graphics.SpriteSheet;
import com.korestudios.royalrenegades.graphics.Texture;
import com.korestudios.royalrenegades.world.World;

import java.util.ArrayList;
import java.util.HashMap;

import static com.korestudios.royalrenegades.constants.GlobalVariables.CENTER;
import static com.korestudios.royalrenegades.constants.VariableConstants.*;
import static com.korestudios.royalrenegades.constants.VariableConstants.TILE_SIZE;

public class EntityRenderer {

    private World world;
    private Shader shader;
    private HashMap<SpriteSheet, ArrayList<Entity>> entityRenderHash = new HashMap<SpriteSheet, ArrayList<Entity>>();

    public EntityRenderer(World world){
        shader = new Shader("shaders/entity.vert", "shaders/entity.frag");
        this.world=world;
    }

    public void render(){
        clear();
        process();
        draw();
    }

    private void clear(){
        entityRenderHash.clear();
    }

    private void process(){
        ArrayList<Entity> entities = world.getCurrChunk().getEntities();
        for(Entity e:entities)
            processEntity(e);

    }

    private void processEntity(Entity e){
        SpriteSheet t = e.getSpriteSheet();
        if(!entityRenderHash.containsKey(t)){
            entityRenderHash.put(t, new ArrayList<Entity>());
        }
        entityRenderHash.get(t).add(e);
    }

    private void draw(){
        shader.enable();
        shader.setUniform1f("depth", DepthConstants.ENTITY_DEPTH);
        for(SpriteSheet t:entityRenderHash.keySet()){
            t.bind(0);
            float partialW = 1f/t.getCols();
            float partialH = 1f/t.getRows();
            ArrayList<Entity> arrayList = entityRenderHash.get(t);
            shader.prime(arrayList.size(), INSTANCE_DATA_LENGTH);
            for(Entity e:arrayList){
                shader.load(
                        e.getPos().x*TILE_SIZE+FRAME_WIDTH/2-CENTER.x,
                        e.getPos().y*TILE_SIZE+FRAME_HEIGHT/2-CENTER.y,
                        e.getTileWidth()*TILE_SIZE,
                        e.getTileHeight()*TILE_SIZE,
                        partialW*e.getTileX(),
                        partialH*e.getTileY(),
                        partialW*e.getTileWidth(),
                        partialH*e.getTileHeight()

                );
            }
            shader.draw(arrayList.size());
            Texture.unbind();
        }
        shader.disable();
    }

}
