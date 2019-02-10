package com.fm.compiler.dynamic.java;

import javax.tools.JavaFileObject;

public interface JavaFileObjectManager {


    JavaFileObject getJavaFileObject(String name);


    void putJavaFileObject(String name, JavaFileObject javaFileObject);

    void compiledJavaObjectHook(JavaObject javaObject);

    void cleanUp();

}
