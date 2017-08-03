package com.korestudios.royalrenegades.utils.time;

import java.util.HashMap;

public class TimeStats {

    public static HashMap<String, Long> times = new HashMap<String, Long>();

    public static void start(String label){
        times.put(label, System.nanoTime());
    }

    public static long stop(String label){
        long currTime = System.nanoTime();
        long lastTime = times.remove(label);
        return currTime-lastTime;
    }

}
