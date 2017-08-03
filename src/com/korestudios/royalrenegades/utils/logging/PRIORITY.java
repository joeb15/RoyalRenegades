package com.korestudios.royalrenegades.utils.logging;

public enum PRIORITY{
    VERBOSE(0),
    DEBUG(1),
    STANDARD(2),
    MINIMAL(3),
    ERRORS(4),
    CRITICAL_ERRORS(5),
    NONE(6);

    private int priority;

    PRIORITY(int priority){
        this.priority=priority;
    }

    public int getPriority(){
        return priority;
    }
}
