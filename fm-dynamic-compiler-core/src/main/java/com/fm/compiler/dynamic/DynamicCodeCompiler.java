package com.fm.compiler.dynamic;

import java.io.File;

public interface DynamicCodeCompiler {
    Class compile(String sCode, String sName) throws Exception;

    Class compile(File file) throws Exception;


    ClassLoader getClassLoader();

    Class loadClass(String name) throws ClassNotFoundException ;
}

