package com.korestudios.royalrenegades.vfs;

import java.io.File;

public class FileSystem {

    public static File getFile(String file){
        return new File(Class.class.getResource("/"+file).getFile());
    }

}
