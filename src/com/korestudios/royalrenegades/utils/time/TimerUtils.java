package com.korestudios.royalrenegades.utils.time;

import java.util.concurrent.CopyOnWriteArrayList;

public class TimerUtils {

    private static CopyOnWriteArrayList<TimerStorage> timers = new CopyOnWriteArrayList<>();
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

    public static void clearTimers() {
        timers.clear();
    }

    public static void removeTimer(int timerID) {
        for(TimerStorage timerStorage:timers)
            if(timerStorage.id==timerID)
                timers.remove(timerStorage);
    }

    private static class TimerStorage {
        private long nextTime;
        private long interval;
        private int numLoops;
        private Timer timer;
        private int id;

        private TimerStorage(long nextTime, long interval, int numLoops, int id, Timer timer){
            this.interval=interval;
            this.nextTime=nextTime;
            this.numLoops=numLoops;
            this.timer=timer;
            this.id=id;
        }
    }
}


