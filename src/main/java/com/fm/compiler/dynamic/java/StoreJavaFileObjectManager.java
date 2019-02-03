package com.fm.compiler.dynamic.java;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;

public class StoreJavaFileObjectManager implements JavaFileObjectManager {

    private File dir;

    public StoreJavaFileObjectManager(File dir) {
        this.dir = dir;
    }

    @Override
    public JavaFileObject getJavaFileObject(String name) {
        File file = new File(dir, name + JavaFileObject.Kind.CLASS.extension);
        JavaFileObject javaFileObject = new JavaObject(file, JavaFileObject.Kind.CLASS);
        try {
            javaFileObject.openOutputStream().write(IOUtils.toByteArray(javaFileObject.toUri()));
            return javaFileObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putJavaFileObject(String name, JavaFileObject javaFileObject) {

    }

}
