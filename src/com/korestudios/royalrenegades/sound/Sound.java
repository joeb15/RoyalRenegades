package com.korestudios.royalrenegades.sound;

import org.lwjgl.openal.AL10;

import java.util.HashMap;

public class Sound {


    int buffer;

    private static HashMap<String, Sound> sounds = new HashMap<>();
    float length;

    public static Sound getSound(String file){
        if(!sounds.containsKey(file))
            new Sound(file);
        return sounds.get(file);
    }

    private Sound(String file){
        buffer = AL10.alGenBuffers();
        SoundManager.addBuffer(buffer);
        WaveData waveData = WaveData.create(file);
        AL10.alBufferData(buffer, waveData.format, waveData.data, waveData.samplerate);
        length = waveData.totalBytes/(float)waveData.bytesPerFrame/waveData.samplerate;
        waveData.dispose();
        sounds.put(file, this);
    }

}
