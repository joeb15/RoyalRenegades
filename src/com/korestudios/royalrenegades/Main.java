package com.korestudios.royalrenegades;

import com.korestudios.royalrenegades.constants.ErrorConstants;
import com.korestudios.royalrenegades.constants.GlobalVariables;
import com.korestudios.royalrenegades.constants.VariableConstants;
import com.korestudios.royalrenegades.graphics.FrameBuffer;
import com.korestudios.royalrenegades.graphics.Texture;
import com.korestudios.royalrenegades.graphics.VertexArray;
import com.korestudios.royalrenegades.guis.Gui;
import com.korestudios.royalrenegades.guis.GuiManager;
import com.korestudios.royalrenegades.input.Input;
import com.korestudios.royalrenegades.renderer.MasterRenderer;
import com.korestudios.royalrenegades.shaders.Shader;
import com.korestudios.royalrenegades.utils.ErrorCallback;
import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;
import com.korestudios.royalrenegades.utils.time.TimeStats;
import com.korestudios.royalrenegades.utils.time.TimerUtils;
import com.korestudios.royalrenegades.world.World;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

import static com.korestudios.royalrenegades.constants.GlobalVariables.*;
import static com.korestudios.royalrenegades.constants.VariableConstants.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main implements Runnable{

    private long window;
    private Thread thread;
    private boolean running;
    private World world;
    private GuiManager guiManager;
    private MasterRenderer masterRenderer;

    public void update(){
        TimeStats.start("Update");
        glfwPollEvents();
        TimerUtils.update();
        world.update();
        update_time_ns = TimeStats.stop("Update");
    }

    public void render(){
        TimeStats.start("Render");
        triangles_drawn_last_frame = triangles_drawn;
        triangles_drawn=0;
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        masterRenderer.render();
        glfwSwapBuffers(window);
        render_time_ns = TimeStats.stop("Render");
    }

    public void init(){
        createWindow();
        Input.init(window);
        world = new World();
        guiManager = new GuiManager();
        guiManager.addGui(new Gui());
        masterRenderer = new MasterRenderer(world, guiManager);
        resize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void createWindow(){
        TimeStats.start("CreateWindow");
        if(!glfwInit()){
            Logger.log(PRIORITY.CRITICAL_ERRORS, "Main", "GLFW Initialization Failed", ErrorConstants.GLFW_INITIALIZATION_ERROR, true);
        }
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        window = glfwCreateWindow(FRAME_WIDTH, FRAME_HEIGHT, FRAME_TITLE,0, 0);
        if(window == 0){
            Logger.log(PRIORITY.CRITICAL_ERRORS, "Main", "GLFW Window Creation Failed", ErrorConstants.GLFW_WINDOW_CREATION_ERROR, true);
        }
        GLFWWindowSizeCallback windowSizeCallback = new GLFWWindowSizeCallback() {
            public void close() {}
            public String getSignature() {return null;}
            public void callback(long args) {
                super.callback(args);
            }
            public void invoke(long window2, int width, int height) {
                resize(width,height);
            }
        };
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        ErrorCallback errorCallback = new ErrorCallback();
        glfwSetErrorCallback(errorCallback);
        glfwSetWindowSizeCallback(window, windowSizeCallback);
        GLUtil.setupDebugMessageCallback(errorCallback);

        glClearColor(0f,0f,0f,1f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glfwShowWindow(window);
        Logger.log(PRIORITY.VERBOSE, "Main", "OpenGL: "+glGetString(GL_VERSION));
        Logger.log(PRIORITY.DEBUG, "Main", "Window creation took "+TimeStats.stop("CreateWindow")/1E6+" ms");
    }

    private void resize(int w, int h){
        FRAME_WIDTH=w;
        FRAME_HEIGHT=h;
        GlobalVariables.PROJECTION_MATRIX.identity().ortho(0f, FRAME_WIDTH, FRAME_HEIGHT, 0, .01f, 10.01f);
        glViewport(0,0,FRAME_WIDTH, FRAME_HEIGHT);
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width()- FRAME_WIDTH)/2,(vidMode.height()- FRAME_HEIGHT)/2);
        int[] monW = new int[1];
        int[] monH = new int[1];
        glfwGetMonitorPhysicalSize(glfwGetPrimaryMonitor(), monW, monH);
        VariableConstants.TILE_SIZE = (int) (25.4 * TILE_SIZE_INCHES * vidMode.width()/monW[0]);
        Shader.loadAll();
    }

    @Override
    public void run(){
        init();
        long lastTime=System.nanoTime();
        int desiredTps = 60;
        long ns = (long) (1E9/desiredTps);
        long currTime, diff, delta = 0;
        int fps=0,tps=0,subSecondFps=0,subSecondTps=0;
        long lastSec=lastTime;
        long lastUpdate=lastTime;
        while(running){
            currTime=System.nanoTime();
            diff = currTime-lastTime;
            delta+=diff;
            if(delta>ns){
                while(delta>ns) {
                    update();
                    tps++;
                    subSecondTps++;
                    delta -= ns;
                }
                render();
                fps++;
                subSecondFps++;
            }else if(!limitFrameRate){
                render();
                fps++;
                subSecondFps++;
            }
            if(currTime-lastSec>1E9){
                lastSec+=1E9;
                fps=0;
                tps=0;
            }
            if(currTime-lastUpdate>time_between_updates){
                double partOfSecond = (currTime-lastUpdate)/1E9;
                lastUpdate+=time_between_updates;
                displayed_render_time=render_time_ns;
                displayed_update_time=update_time_ns;
                displayed_fps = subSecondFps/partOfSecond;
                displayed_tps = subSecondTps/partOfSecond;
                subSecondFps=0;
                subSecondTps=0;
            }
            if(glfwWindowShouldClose(window)){
                running=false;
            }
            lastTime=currTime;
        }
        cleanup();
    }

    private void cleanup() {
        Texture.cleanup();
        Shader.cleanup();
        VertexArray.cleanup();
        FrameBuffer.cleanup();
    }

    public void start(){
        running=true;
        thread = new Thread(this, "GameThread");
        thread.start();
    }

    public static void main(String[] args){
        new Main().start();
    }

}