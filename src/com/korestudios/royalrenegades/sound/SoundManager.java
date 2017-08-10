package com.korestudios.royalrenegades.sound;

import com.korestudios.royalrenegades.constants.ErrorConstants;
import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.EXTThreadLocalContext.alcSetThreadContext;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SoundManager {

    private static ArrayList<Integer> buffers = new ArrayList<>();
    private static ArrayList<Integer> sources = new ArrayList<>();

    private static long device, context;

    public static void setListenerPosAndVel(float x, float y, float xv, float yv){
        AL10.alListener3f(AL10.AL_POSITION, x, y, 0);
        AL10.alListener3f(AL10.AL_VELOCITY, x, y, 0);

    }

    public static void init(){
        device = alcOpenDevice((ByteBuffer)null);
        if(device == NULL){
            Logger.log(PRIORITY.CRITICAL_ERRORS, "SoundManager", "Unable to open ALC device", ErrorConstants.ALC_OPEN_ERROR, true);
        }
        ALCCapabilities capabilities = ALC.createCapabilities(device);
        String defaultDeviceSpecifier = alcGetString(NULL, ALC_DEFAULT_DEVICE_SPECIFIER);
        context = alcCreateContext(device, (IntBuffer) null);
        alcSetThreadContext(context);
        AL.createCapabilities(capabilities);
        AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);

    }

    public static void clearSources(){
        for(int i:sources)
            alDeleteSources(i);
        AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }

    public static void cleanup(){
        clearSources();
        for(int buffer:buffers)
            AL10.alDeleteBuffers(buffer);
        alcMakeContextCurrent(NULL);
        alcDestroyContext(context);
        alcCloseDevice(device);
        ALC.destroy();
    }

    static void addBuffer(int buffer){
        buffers.add(buffer);
    }

    static void addSource(int source){
        sources.add(source);
    }

}
