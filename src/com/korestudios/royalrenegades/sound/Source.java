package com.korestudios.royalrenegades.sound;

import com.korestudios.royalrenegades.utils.time.TimerUtils;

import static org.lwjgl.openal.AL10.*;

public class Source {

    private int sourceID;

    private Sound currSound;
    private int currTimer = -1;
    public Source(float x, float y, float z){
        sourceID = alGenSources();
        alSourcef(sourceID, AL_GAIN, 1);
        alSourcef(sourceID, AL_PITCH, 1);
        alSource3f(sourceID, AL_POSITION, x, y,0);
        alSourcei(sourceID, AL_LOOPING, AL_TRUE);
        SoundManager.addSource(sourceID);
    }

    public void playLoop(Sound sound){
        play(sound, -1);
    }

    public void play(Sound sound, int loops){
        currSound=sound;
        alSourcei(sourceID, AL_BUFFER, sound.buffer);
        if(loops!=-1)
            TimerUtils.addTimer(this::stop, (long)(1E9*sound.length * loops), 1);
        alSourcePlay(sourceID);
    }

    public void play(Sound sound){
        play(sound, 1);
    }

    public void play(){
        if(currSound==null)
            return;
        TimerUtils.removeTimer(currTimer);
        alSourcePlay(sourceID);

    }

    public void pause(){
        if(currSound==null)
            return;
        TimerUtils.removeTimer(currTimer);
        alSourcePause(sourceID);
    }

    public void stop(){
        if(currSound==null)
            return;
        TimerUtils.removeTimer(currTimer);
        alSourceStop(sourceID);
    }

}
