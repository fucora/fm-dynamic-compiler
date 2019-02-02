package com.fm.compiler.dynamic.java;

import javax.tools.JavaFileObject;

public interface JavaFileObjectManager {


    JavaFileObject getJavaFileObject(String name);


    void putJavaFileObject(String name, JavaFileObject javaFileObject);

    default void putLoadJavaFileObject(String name, JavaFileObject javaFileObject){
        putJavaFileObject(name, javaFileObject);
        loadJavaFileObject(javaFileObject);
    }

    void loadJavaFileObject(JavaFileObject javaFileObject);
}
