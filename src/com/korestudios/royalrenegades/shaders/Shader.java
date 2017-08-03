package com.korestudios.royalrenegades.shaders;

import com.korestudios.royalrenegades.constants.ErrorConstants;
import com.korestudios.royalrenegades.constants.GlobalVariables;
import com.korestudios.royalrenegades.graphics.VertexArray;
import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;
import com.korestudios.royalrenegades.utils.ShaderUtils;
import org.joml.*;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static com.korestudios.royalrenegades.constants.GlobalVariables.PROJECTION_MATRIX;
import static com.korestudios.royalrenegades.constants.VariableConstants.INSTANCED_ATTRIBS;
import static com.korestudios.royalrenegades.constants.VariableConstants.INSTANCE_DATA_LENGTH;
import static com.korestudios.royalrenegades.constants.VariableConstants.MAX_INSTANCES;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by joe on 7/13/17.
 */
public class Shader {

    private final int id;
    private boolean enabled = false;
    private HashMap<String, Integer> uniformLocations = new HashMap<String, Integer>();
    private static ArrayList<Shader> shaders = new ArrayList<Shader>();
    private static VertexArray rectangle, rectangleInstance;
    private static int instancedVBO;

    private static float[] vertices, textures;
    private static byte[] indices;

    public Shader(String vertex, String fragment){
        id = ShaderUtils.load(vertex, fragment);
        shaders.add(this);
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

        vertices = new float[]{
                0, 0, 0,
                0, 1, 0,
                1, 1, 0,
                1, 0, 0
        };
        indices = new byte[]{
                0,1,2,2,3,0
        };
        textures = new float[]{
                0,0,
                0,1,
                1,1,
                1,0,
        };
        rectangle = new VertexArray(vertices, indices, textures);

        rectangleInstance = Shader.createInstance();
        instancedVBO = rectangleInstance.createEmptyVBO(INSTANCE_DATA_LENGTH * MAX_INSTANCES);
        rectangleInstance.addInstancedAttribute(rectangleInstance.getVAO(), instancedVBO, INSTANCED_ATTRIBS, 4, INSTANCE_DATA_LENGTH, 0);
        rectangleInstance.addInstancedAttribute(rectangleInstance.getVAO(), instancedVBO, INSTANCED_ATTRIBS + 1, 4, INSTANCE_DATA_LENGTH, 4);

        for(Shader s:shaders) {
            s.enable();
            s.setUniformMat4f("pr_matrix", PROJECTION_MATRIX);
            s.setUniform1i("tex", 0);
            s.disable();
        }
    }

    public static VertexArray createInstance(){
        return new VertexArray(vertices, indices, textures);
    }

    public static void cleanup(){
        for(Shader s:shaders)
            glDeleteProgram(s.id);
    }

    public static void render(){
        rectangle.draw();
        GlobalVariables.triangles_drawn+=2;
    }

    public void prime(int size, int dataLength){
        rectangleInstance.prime(size, dataLength);
    }

    public void load(float... dataToLoad){
        rectangleInstance.load(dataToLoad);
    }

    public void enable(){
        enable(rectangle);
    }

    public void enableInstanced(){
        enable(rectangleInstance);
    }

    private void enable(VertexArray v){
        for(Shader s:shaders)
            s.enabled=false;
        v.bind();
        glUseProgram(id);
        enabled=true;
    }

    public void disable(){
        rectangle.unbind();
        glUseProgram(0);
        enabled=false;
    }

    public void disableInstanced(){
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

    public void draw(int size) {
        rectangleInstance.updateVBO(instancedVBO);
        rectangleInstance.render(size);
    }
}