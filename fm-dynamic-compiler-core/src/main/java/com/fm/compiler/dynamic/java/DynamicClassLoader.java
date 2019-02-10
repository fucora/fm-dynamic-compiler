package com.fm.compiler.dynamic.java;

import javax.tools.JavaFileObject;

public class DynamicClassLoader extends ClassLoader {


    private JavaFileObjectManager manager;


    public DynamicClassLoader(JavaFileObjectManager manager, ClassLoader parent) {
        super(parent);
        this.manager = manager;
    }

    public DynamicClassLoader(JavaFileObjectManager manager) {
        this.manager = manager;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        JavaFileObject fileObject = manager.getJavaFileObject(name);
        if (fileObject != null) {
            byte[] bytes = ((JavaObject) fileObject).getCompiledBytes();
            return defineClass(name, bytes, 0, bytes.length);
        }
        try {
            return ClassLoader.getSystemClassLoader().loadClass(name);
        } catch (Exception e) {
            return super.findClass(name);
        }
    }
}