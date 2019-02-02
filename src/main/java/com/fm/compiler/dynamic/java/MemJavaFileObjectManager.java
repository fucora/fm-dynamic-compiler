package com.fm.compiler.dynamic.java;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import javax.tools.JavaFileObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class MemJavaFileObjectManager implements JavaFileObjectManager {
    private Cache<String, JavaFileObject> fileObjects;

    public MemJavaFileObjectManager() {
        fileObjects = Caffeine.newBuilder()
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
    public void loadJavaFileObject(JavaFileObject javaFileObject) {

    }


    public void cleanUp() {
        fileObjects.cleanUp();
    }
}
