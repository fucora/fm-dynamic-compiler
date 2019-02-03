package com.fm.compiler.dynamic.java;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;

public class DynamicJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private JavaFileObjectManager javaFileObjectManager;

    protected DynamicJavaFileManager(JavaFileManager fileManager, JavaFileObjectManager javaFileObjectManager) {
        super(fileManager);
        this.javaFileObjectManager = javaFileObjectManager;
    }

    @Override
    public JavaFileObject getJavaFileForInput(Location location, String className, JavaFileObject.Kind kind) throws IOException {
        JavaFileObject javaFileObject = javaFileObjectManager.getJavaFileObject(className);
        if (javaFileObject == null) {
            super.getJavaFileForInput(location, className, kind);
        }
        return javaFileObject;
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String qualifiedClassName, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        JavaFileObject javaFileObject = new JavaObject(qualifiedClassName, kind);
        javaFileObjectManager.putJavaFileObject(qualifiedClassName, javaFileObject);
        return javaFileObject;
    }

}
