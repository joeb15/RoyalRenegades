package com.korestudios.royalrenegades.shaders;

import com.korestudios.royalrenegades.constants.ErrorConstants;
import com.korestudios.royalrenegades.graphics.VertexArray;
import com.korestudios.royalrenegades.utils.ShaderUtils;
import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;
import org.joml.*;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static com.korestudios.royalrenegades.constants.GlobalVariables.PROJECTION_MATRIX;
import static com.korestudios.royalrenegades.constants.VariableConstants.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by joe on 7/13/17.
 */
public class Shader {

    private final int id;
    private boolean enabled = false;
    private HashMap<String, Integer> uniformLocations = new HashMap<String, Integer>();
    private static ArrayList<Shader> shaders = new ArrayList<Shader>();
    private VertexArray rectangleInstance;
    private int instancedVBO, totalData;

    private static float[] vertices = new float[]{
            0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0
    };
    private static byte[] indices = new byte[]{
            0,1,2,2,3,0
    };
    private static float[] textures = new float[]{
            0,0, 0,1, 1,1, 1,0,
    };
    private boolean usesTexture = true;

    public Shader(String vertex, String fragment, int... parts){
        rectangleInstance = createInstance();
        for(int i:parts)
            totalData+=i;
        instancedVBO = rectangleInstance.createEmptyVBO(totalData * MAX_INSTANCES);
        int offset=0;
        int attrib=0;
        for(int i:parts){
            for(int j=0;j<i;j+=4){
                int dif = i-j;
                if(dif>4)
                    dif=4;
                rectangleInstance.addInstancedAttribute(rectangleInstance.getVAO(), instancedVBO, INSTANCED_ATTRIBS+(attrib++), dif, totalData, offset);
                offset+=dif;
            }
        }
        id = ShaderUtils.load(vertex, fragment);
        shaders.add(this);

    }

    public Shader(String vertex, String fragment){
        this(vertex, fragment, INSTANCE_DATA_LENGTH);
    }

    public int getUniform(String name){
        if(uniformLocations.containsKey(name))
            return uniformLocations.get(name);
        int loc = glGetUniformLocation(id, name);
        if(loc == -1){
            Logger.log(PRIORITY.ERRORS, "Shader", "Unable to find/did not use uniform variable: "+name, ErrorConstants.MISSING_UNUSED_UNIFORM, false);
        }
        uniformLocations.put(name, loc);
        return loc;
    }

    public static void loadAll(){
        for(Shader s:shaders) {
            s.enable();
            s.setUniformMat4f("pr_matrix", PROJECTION_MATRIX);
            if(s.usesTexture)
                s.setUniform1i("tex", 0);
            s.disable();
        }
    }

    public VertexArray createInstance(){
        return new VertexArray(vertices, indices, textures);
    }

    public static void cleanup(){
        for(Shader s:shaders)
            glDeleteProgram(s.id);
    }

    public void prime(int size){
        rectangleInstance.prime(size, totalData);
    }

    public void draw(int size) {
        rectangleInstance.updateVBO(instancedVBO);
        rectangleInstance.render(size);
    }

    public void load(float... dataToLoad){
        rectangleInstance.load(dataToLoad);
    }

    public void enable(){
        for(Shader s:shaders)
            s.enabled=false;
        rectangleInstance.bind();
        glUseProgram(id);
        enabled=true;
    }

    public void disable(){
        rectangleInstance.unbind();
        glUseProgram(0);
        enabled=false;
    }

    public void setUniformMat4f(String name, Matrix4f mat){
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        mat.get(fb);
        glUniformMatrix4fv(getUniform(name), false, fb);
    }

    public void setUniform1i(String name, int x){
        if(!enabled)enable();
        glUniform1i(getUniform(name), x);
    }

    public void setUniform2i(String name, int x, int y){
        if(!enabled)enable();
        glUniform2i(getUniform(name), x, y);
    }
    public void setUniform2i(String name, Vector2i value){
        if(!enabled)enable();
        glUniform2i(getUniform(name), value.x, value.y);
    }

    public void setUniform3i(String name, int x, int y, int z){
        if(!enabled)enable();
        glUniform3i(getUniform(name), x, y, z);
    }
    public void setUniform3i(String name, Vector3i value){
        if(!enabled)enable();
        glUniform3i(getUniform(name), value.x, value.y, value.z);
    }

    public void setUniform1f(String name, float value){
        if(!enabled)enable();
        glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, float x, float y){
        if(!enabled)enable();
        glUniform2f(getUniform(name), x, y);
    }
    public void setUniform2f(String name, Vector2f value){
        if(!enabled)enable();
        glUniform2f(getUniform(name), value.x, value.y);
    }

    public void setUniform3f(String name, Vector3f value){
        if(!enabled)enable();
        glUniform3f(getUniform(name), value.x, value.y, value.z);
    }
    public void setUniform3f(String name, float x, float y, float z){
        if(!enabled)enable();
        glUniform3f(getUniform(name), x, y, z);
    }

    public Shader doesntUseTexture() {
        this.usesTexture=false;
        return this;
    }
}
