package com.korestudios.royalrenegades.graphics;

import com.korestudios.royalrenegades.constants.GlobalVariables;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static com.korestudios.royalrenegades.constants.VariableConstants.TCOORD_ATTRIB;
import static com.korestudios.royalrenegades.constants.VariableConstants.VERTEX_ATTRIB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

/**
 * Created by joe on 7/13/17.
 */
public class VertexArray {

    private int count;
    private int vao, vbo, ibo, tbo;
    private ArrayList<Integer> instancedVBOS = new ArrayList<Integer>();

    private static ArrayList<Integer> vbos = new ArrayList<Integer>();
    private static ArrayList<Integer> vaos = new ArrayList<Integer>();

    private float[] data;
    private int dataPointer;
    private FloatBuffer buffer;

    public VertexArray(float[] vertices, byte[] indices, float[] textureCoordinates){
        count=indices.length;
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
        glVertexAttribPointer(VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0);

        tbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tbo);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(textureCoordinates), GL_STATIC_DRAW);
        glVertexAttribPointer(TCOORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, createBuffer(indices), GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
        glBindVertexArray(0);

        vaos.add(vao);
        vbos.add(ibo);
        vbos.add(tbo);
        vbos.add(vbo);
    }

    public void render(int i){
        bind();
        draw(i);
    }

    public int getVAO(){
        return vao;
    }


    public void draw(int i){
        glDrawElementsInstanced(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0, i);
        GlobalVariables.triangles_drawn+=2*i;
    }

    public void updateVBO(int vbo, float[] data, FloatBuffer buffer){
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, buffer.capacity()*4, GL_DYNAMIC_DRAW);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void updateVBO(int vbo){
        updateVBO(vbo, data, buffer);
    }

    public int createEmptyVBO(int floatCount){
        int vbo = glGenBuffers();
        vbos.add(vbo);
        buffer = BufferUtils.createFloatBuffer(floatCount);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, floatCount*4, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return vbo;
    }

    public void addInstancedAttribute(int vao, int vbo, int attribute, int dataSize, int instancedDataLength, int offset){
        instancedVBOS.add(attribute);
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(attribute, dataSize, GL_FLOAT, false, instancedDataLength*4, offset*4);
        glVertexAttribDivisor(attribute, 1);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void bind(){
        glBindVertexArray(vao);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glEnableVertexAttribArray(VERTEX_ATTRIB);
        glEnableVertexAttribArray(TCOORD_ATTRIB);
        for(int i:instancedVBOS)
            glEnableVertexAttribArray(i);
    }

    public void unbind(){
        glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        for(int i:instancedVBOS)
            glDisableVertexAttribArray(i);
        glDisableVertexAttribArray(VERTEX_ATTRIB);
        glDisableVertexAttribArray(TCOORD_ATTRIB);
    }

    public static void cleanup(){
        for(int i:vaos)
            glDeleteVertexArrays(i);
        for(int i:vbos)
            glDeleteBuffers(i);
    }

    public ByteBuffer createBuffer(byte[] bytes){
        ByteBuffer bb = BufferUtils.createByteBuffer(bytes.length);
        bb.put(bytes);
        bb.flip();
        return bb;
    }

    public FloatBuffer createBuffer(float[] floats){
        FloatBuffer fb = BufferUtils.createFloatBuffer(floats.length);
        fb.put(floats);
        fb.flip();
        return fb;
    }

    public void load(float... dataToLoad){
        for(float f:dataToLoad)
            data[dataPointer++]=f;
    }

    public void prime(int size, int dataLength) {
        data = new float[size*dataLength];
        dataPointer=0;
    }
}
