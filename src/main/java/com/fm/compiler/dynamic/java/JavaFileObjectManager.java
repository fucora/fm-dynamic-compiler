package com.fm.compiler.dynamic.java;

import javax.tools.JavaFileObject;
import java.io.IOException;

public interface JavaFileObjectManager {


    JavaFileObject getJavaFileObject(String name);


    void putJavaFileObject(String name, JavaFileObject javaFileObject);

}
