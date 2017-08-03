package com.korestudios.royalrenegades.utils;

import com.korestudios.royalrenegades.constants.ErrorConstants;
import com.korestudios.royalrenegades.utils.logging.Logger;
import com.korestudios.royalrenegades.utils.logging.PRIORITY;

import java.io.*;

/**
 * Created by joe on 7/13/17.
 */
public class FileUtils {
    private FileUtils(){}

    public static String loadAsString(String file){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String buffer = "";
            while((buffer=br.readLine())!=null){
                sb.append(buffer);
                sb.append('\n');
            }
        } catch (FileNotFoundException e) {
            Logger.log(PRIORITY.CRITICAL_ERRORS, "FileUtils", e.getMessage(), ErrorConstants.FNF_ERROR, true);
        } catch (IOException e) {
            Logger.log(PRIORITY.CRITICAL_ERRORS, "FileUtils", e.getMessage(), ErrorConstants.IO_ERROR, true);
        }
        return sb.toString();
    }

    public static void writeToFile(String fileName, String info) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(info);
        bw.close();
    }

    public static void appendToFile(String fileName, String info) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.append(info);
        bw.close();
    }

    public static boolean exists(String s) {
        File f = new File(s);
        return f.exists();
    }
}
