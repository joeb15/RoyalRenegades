package com.korestudios.royalrenegades.utils;

import com.korestudios.royalrenegades.constants.ErrorConstants;
import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by joe on 7/13/17.
 */
public class ShaderUtils {

    private ShaderUtils(){}

    public static int load(String vertPath, String fragPath){
        String vert = FileUtils.loadAsString(vertPath);
        String frag = FileUtils.loadAsString(fragPath);
        return create(vert,frag);
    }

    private static int create(String vert, String frag) {
        int program = glCreateProgram();
        int vertID = glCreateShader(GL_VERTEX_SHADER);
        int fragID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertID,vert);
        glShaderSource(fragID,frag);

        glCompileShader(vertID);
        if(glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE){
            Logger.log(PRIORITY.CRITICAL_ERRORS, "ShaderUtils", "Failed to compile Vertex Shader.\n"+
                    glGetShaderInfoLog(vertID), ErrorConstants.VERTEX_SHADER_COMPILE_ERROR, true);
        }

        glCompileShader(fragID);
        if(glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE){
            Logger.log(PRIORITY.CRITICAL_ERRORS, "ShaderUtils", "Failed to compile Fragment Shader.\n"+
                    glGetShaderInfoLog(fragID), ErrorConstants.FRAGMENT_SHADER_COMPILE_ERROR, true);
        }

        glAttachShader(program, vertID);
        glAttachShader(program, fragID);

        glLinkProgram(program);
        if(glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE){
            Logger.log(PRIORITY.CRITICAL_ERRORS, "ShaderUtils", "Failed to link shader program.\n"+
                    glGetProgramInfoLog(program), ErrorConstants.PROGRAM_LINK_ERROR, true);
        }

        glValidateProgram(program);
        if(glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE){
            Logger.log(PRIORITY.CRITICAL_ERRORS, "ShaderUtils", "Failed to validate shader program.\n"+
                    glGetProgramInfoLog(program), ErrorConstants.PROGRAM_VALIDATE_ERROR, true);
        }

        glDeleteShader(vertID);
        if(glGetShaderi(vertID, GL_DELETE_STATUS) == GL_FALSE){
            Logger.log(PRIORITY.ERRORS, "ShaderUtils", "Failed to delete Vertex Shader.\n"+
                    glGetShaderInfoLog(vertID), ErrorConstants.VERTEX_DELETE_ERROR, false);
        }
        glDeleteShader(fragID);
        if(glGetShaderi(fragID, GL_DELETE_STATUS) == GL_FALSE){
            Logger.log(PRIORITY.ERRORS, "ShaderUtils", "Failed to delete Fragment Shader.\n"+
                glGetShaderInfoLog(fragID), ErrorConstants.FRAGMENT_DELETE_ERROR, false);
        }

        return program;
    }

}
