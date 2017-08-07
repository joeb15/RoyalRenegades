package com.korestudios.royalrenegades.renderer;

import com.korestudios.royalrenegades.constants.DepthConstants;
import com.korestudios.royalrenegades.entities.Entity;
import com.korestudios.royalrenegades.graphics.SpriteSheet;
import com.korestudios.royalrenegades.graphics.Texture;
import com.korestudios.royalrenegades.shaders.Shader;
import com.korestudios.royalrenegades.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.korestudios.royalrenegades.constants.GlobalVariables.*;
import static com.korestudios.royalrenegades.constants.VariableConstants.delta;

public class EntityRenderer {

    private World world;
    private Shader shader;
    private HashMap<SpriteSheet, ArrayList<Entity>> entityRenderHash = new HashMap<SpriteSheet, ArrayList<Entity>>();

    public EntityRenderer(World world){
        shader = new Shader("shaders/entity.vert", "shaders/entity.frag", 4, 4, 1);
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
        CopyOnWriteArrayList<Entity> entities = world.getCurrChunk().getEntities();
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
            shader.prime(arrayList.size());
            for(Entity e:arrayList){
                shader.load(
                        e.getPos().x*TILE_SIZE+FRAME_WIDTH/2-CENTER.x,
                        e.getPos().y*TILE_SIZE+FRAME_HEIGHT/2-CENTER.y,
                        e.getWidth()*TILE_SIZE,
                        e.getHeight()*TILE_SIZE,
                        partialW*e.getTileX()+delta,
                        partialH*e.getTileY()+delta,
                        partialW*e.getTileWidth()-2*delta,
                        partialH*e.getTileHeight()-2*delta,
                        e.getRotation()
                );
            }
            shader.draw(arrayList.size());
            Texture.unbind();
        }
        shader.disable();
    }

}
