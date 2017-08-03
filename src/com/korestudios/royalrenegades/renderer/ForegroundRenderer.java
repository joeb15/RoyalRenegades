package com.korestudios.royalrenegades.renderer;

import com.korestudios.royalrenegades.constants.DepthConstants;
import com.korestudios.royalrenegades.shaders.Shader;
import com.korestudios.royalrenegades.graphics.SpriteSheet;
import com.korestudios.royalrenegades.graphics.Texture;
import com.korestudios.royalrenegades.tiles.Tile;
import com.korestudios.royalrenegades.tiles.metadata.MetaData;
import com.korestudios.royalrenegades.world.Chunk;
import com.korestudios.royalrenegades.world.World;
import org.joml.Vector4i;

import java.util.ArrayList;
import java.util.HashMap;

import static com.korestudios.royalrenegades.constants.GlobalVariables.CENTER;
import static com.korestudios.royalrenegades.constants.GlobalVariables.delta;
import static com.korestudios.royalrenegades.constants.VariableConstants.*;

public class ForegroundRenderer{

    private Shader shader;
    private World world;

    private HashMap<SpriteSheet, ArrayList<Vector4i>> renderHash = new HashMap<SpriteSheet, ArrayList<Vector4i>>();

    public ForegroundRenderer(World world){
        this.world = world;
        shader = new Shader("shaders/foreground.vert", "shaders/foreground.frag");
    }

    public void render(){
        renderHash.clear();
        primeChunk();
        shader.enableInstanced();
        shader.setUniform1f("depth", DepthConstants.FOREGROUND_DEPTH);
        for(SpriteSheet t:renderHash.keySet()){
            t.bind(0);
            float partialW = 1f/t.getCols();
            float partialH = 1f/t.getRows();
            ArrayList<Vector4i> arrayList = renderHash.get(t);

            shader.prime(arrayList.size(), INSTANCE_DATA_LENGTH);
            for (Vector4i pos : arrayList) {
                shader.load(
                        pos.x * TILE_SIZE - CENTER.x + FRAME_WIDTH / 2,
                        pos.y * TILE_SIZE - CENTER.y + FRAME_HEIGHT / 2,
                        TILE_SIZE,
                        TILE_SIZE,
                        partialW*pos.z+delta,
                        partialH*pos.w+delta,
                        partialW-2*delta,
                        partialH-2*delta
                );
            }
            shader.draw(arrayList.size());
            Texture.unbind();
        }
        shader.disableInstanced();
    }

    public void primeChunk(){
        Chunk currChunk = world.getCurrChunk();
        Vector4i minMax = currChunk.getVisibleArea(CENTER);
        for(int y=minMax.y;y<minMax.w;y++){
            for(int x = minMax.x;x<minMax.z;x++){
                Tile tile = currChunk.getTile(x, y);
                SpriteSheet t = tile.getSpriteSheet();
                if(!renderHash.containsKey(t)){
                    renderHash.put(t, new ArrayList<Vector4i>());
                }
                MetaData metaData = currChunk.getMetadata(x, y);
                renderHash.get(t).add(new Vector4i(x, y, tile.getTileX(metaData), tile.getTileY(metaData)));
            }
        }
    }
}
