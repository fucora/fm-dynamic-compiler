package com.fm.compiler.dynamic.java;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class StoreJavaFileObjectManager extends MemJavaFileObjectManager {

    private static final Logger log = LoggerFactory.getLogger(StoreJavaFileObjectManager.class);

    private File targetDir;

    public StoreJavaFileObjectManager(File targetDir) {
        this.targetDir = targetDir;
    }

    @Override
    public JavaFileObject getJavaFileObject(String name) {
        JavaFileObject javaFileObject = super.getJavaFileObject(name);
        if (javaFileObject != null) {
            return javaFileObject;
        }

        File file = new File(targetDir, name + JavaFileObject.Kind.CLASS.extension);
        if (file.exists()) {
            javaFileObject = new JavaObject(file, JavaFileObject.Kind.CLASS);
            try {
                javaFileObject.openOutputStream().write(IOUtils.toByteArray(javaFileObject.toUri()));
                return javaFileObject;
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("没有找到class文件 " + file.getAbsolutePath());
        }
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        try {
            FileUtils.cleanDirectory(targetDir);
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void compiledJavaObjectHook(JavaObject javaObject) {
        List<JavaObject> javaObjects = CompileContext.currentCompileContext().getCompiledObjects();
        for (JavaObject compiledObject : javaObjects) {
            outputCompiledClassContent(compiledObject);
        }
    }


    private void outputCompiledClassContent(JavaObject javaObject) {
        File file = new File(targetDir, javaObject.getJavaName() + JavaFileObject.Kind.CLASS.extension);
        try {
            FileUtils.writeByteArrayToFile(file, javaObject.getCompiledBytes());
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }
}
