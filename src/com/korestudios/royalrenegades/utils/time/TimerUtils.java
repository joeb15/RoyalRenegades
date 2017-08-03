package com.korestudios.royalrenegades.utils.time;

import java.util.concurrent.CopyOnWriteArrayList;

public class TimerUtils {

    private static CopyOnWriteArrayList<TimerStorage> timers = new CopyOnWriteArrayList<TimerStorage>();
    private static int currID = 0;

    public static void update(){
        long curr = System.nanoTime();
        for(TimerStorage ts:timers){
            if(curr>ts.nextTime){
                ts.nextTime+=ts.interval;
                ts.timer.tick();
                if(ts.numLoops!=-1)
                    ts.numLoops--;
                if(ts.numLoops==0)
                    timers.remove(ts);
            }
        }
    }

    public static int addTimer(Timer timer, long nanos){
        return addTimer(timer, nanos, -1);
    }

    public static int addTimer(Timer timer, long nanos, int loops){
        TimerStorage ts = new TimerStorage(System.nanoTime()+nanos, nanos, loops, currID, timer);
        timers.add(ts);
        return currID++;
    }

    private static class TimerStorage {
        public long nextTime;
        public long interval;
        public int numLoops;
        public Timer timer;
        public int id;

        public TimerStorage(long nextTime, long interval, int numLoops, int id, Timer timer){
            this.interval=interval;
            this.nextTime=nextTime;
            this.numLoops=numLoops;
            this.timer=timer;
            this.id=id;
        }
    }
}


