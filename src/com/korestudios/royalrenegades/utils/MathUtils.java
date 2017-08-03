package com.korestudios.royalrenegades.utils;

public class MathUtils {

    public static String round(double val, int places){
        String str = ""+val;
        int loc = str.indexOf(".");
        String num = (int)val+".";
        for(int i=loc+1;i<str.length() && i<loc+1+places;i++)
            num+=str.charAt(i);
        return num;
    }

    public static String toTimeString(long nanoSeconds){
        if(nanoSeconds>1E8){
            int val = (int)(nanoSeconds/1E7);
            int seconds = val/100;
            int decimal = val%100;
            return seconds+"."+decimal+"s";
        }else if(nanoSeconds>1E5){
            int val = (int)(nanoSeconds/1E4);
            int seconds = val/100;
            int decimal = val%100;
            return seconds+"."+decimal+"ms";
        }else if(nanoSeconds>1E2){
            int val = (int)(nanoSeconds/10);
            int seconds = val/100;
            int decimal = val%100;
            return seconds+"."+decimal+"\u00B5s";
        }else{
            int val = (int)(nanoSeconds*100);
            int seconds = val/100;
            int decimal = val%100;
            return seconds+"."+decimal+"ns";
        }
    }

    public static float moveCloser(float curr, float desired, float factor){
        if(Math.abs(curr-desired)>100)
            return desired;
        return (desired-curr)*factor+curr;
    }

}
