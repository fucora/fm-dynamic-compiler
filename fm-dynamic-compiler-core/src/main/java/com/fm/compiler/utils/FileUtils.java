package com.fm.compiler.utils;

import java.io.File;

public class FileUtils {


    public static String getNameByIgnoreSuffix(File file) {
        String fileName = file.getName();
        int len = fileName.lastIndexOf(".");
        return fileName.substring(0, len);
    }
}
