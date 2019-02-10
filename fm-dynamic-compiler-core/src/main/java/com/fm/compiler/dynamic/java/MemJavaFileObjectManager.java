package com.fm.compiler.dynamic.java;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import javax.tools.JavaFileObject;
import java.util.concurrent.TimeUnit;

public class MemJavaFileObjectManager implements JavaFileObjectManager {
    private Cache<String, JavaFileObject> fileObjects;

    public MemJavaFileObjectManager() {
        fileObjects = Caffeine.newBuilder()
                .maximumSize(50)
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public JavaFileObject getJavaFileObject(String name) {
        return fileObjects.getIfPresent(name);
    }

    @Override
    public void putJavaFileObject(String name, JavaFileObject javaFileObject) {
        fileObjects.put(name, javaFileObject);
    }

    @Override
    public void cleanUp() {
        fileObjects.cleanUp();
    }

    @Override
    public void compiledJavaObjectHook(JavaObject javaObject) {

    }
}
