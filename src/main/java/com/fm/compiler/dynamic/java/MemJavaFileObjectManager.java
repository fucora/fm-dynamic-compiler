package com.fm.compiler.dynamic.java;

import javax.tools.JavaFileObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemJavaFileObjectManager implements JavaFileObjectManager {
    private Map<String, JavaFileObject> fileObjects = new ConcurrentHashMap<>();

    @Override
    public JavaFileObject getJavaFileObject(String name) {
        return fileObjects.get(name);
    }

    @Override
    public void putJavaFileObject(String name, JavaFileObject javaFileObject) {
        fileObjects.put(name, javaFileObject);
    }

    @Override
    public void loadJavaFileObject(JavaFileObject javaFileObject) {

    }


    public void clear() {
        fileObjects.clear();
    }
}
