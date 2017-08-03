package com.korestudios.royalrenegades.utils.logging;

import java.io.PrintStream;
import java.util.Calendar;

import static com.korestudios.royalrenegades.utils.logging.PRIORITY.*;

/**
 * Created by joe on 7/16/17.
 */
public class Logger {

    public static PRIORITY CURR_PRIORITY = DEBUG;


    public static void log(PRIORITY priority, String caller, String message){
        log(priority,caller,message,-1,false);
    }
    public static void log(PRIORITY priority, String caller, String message, int ERROR_CODE, boolean fatal){
        if(CURR_PRIORITY==NONE)
            return;
        if(priority.getPriority() >= CURR_PRIORITY.getPriority()){
            String timeTag = getTimeTag();
            String[] lines = message.split("\n");
            PrintStream output = System.out;
            if(priority.getPriority()>=PRIORITY.ERRORS.getPriority() || fatal)
                output=System.err;
            if(ERROR_CODE!=-1){
                System.err.println(caller+" threw a"+(fatal?" fatal":"n")+" error #"+ERROR_CODE);
            }
            for(String line:lines){
                output.println(timeTag+" - ["+caller+"] - "+message);
            }
            if(fatal)
                System.exit(ERROR_CODE);
        }
    }

    private static String getTimeTag(){
        Calendar c = Calendar.getInstance();
        String h = ""+c.get(Calendar.HOUR_OF_DAY);
        String m = ""+c.get(Calendar.MINUTE);
        String s = ""+c.get(Calendar.SECOND);
        if(h.length()==1)
            h="0"+h;
        if(m.length()==1)
            m="0"+m;
        if(s.length()==1)
            s="0"+s;
        String timeTag = "["+h+":"+m+":"+s+"]";
        return timeTag;
    }

}

