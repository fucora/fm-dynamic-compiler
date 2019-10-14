package com.fm.compiler.dynamic.java;

import com.fm.compiler.dynamic.DynamicCodeCompiler;
import com.taobao.arthas.compiler.DynamicCompiler;

import javax.tools.JavaFileObject;
import java.io.File;
import java.util.Iterator;
import java.util.Map;

public class JavaArthasCompiler implements DynamicCodeCompiler {

    private ClassLoader classLoader;

    public JavaArthasCompiler(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public JavaArthasCompiler(){
        this(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public Class compile(String sCode, String sName) throws Exception {
        DynamicCompiler dynamicCompiler = new DynamicCompiler(getClassLoader());
        dynamicCompiler.addSource(sName, sCode);
        Map<String, Class<?>> map = dynamicCompiler.build();
        return map.get(sName);
    }

    @Override
    public Class compile(File file) throws Exception {
        JavaObject javaFileObject = new JavaObject(file, JavaFileObject.Kind.SOURCE);
        DynamicCompiler dynamicCompiler = new DynamicCompiler(getClassLoader());
        dynamicCompiler.addSource(javaFileObject);
        Map<String, Class<?>> map = dynamicCompiler.build();
        Iterator<Class<?>> iter = map.values().iterator();
        if (iter.hasNext()) {
            return iter.next();
        }
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader == null ? Thread.currentThread().getContextClassLoader() : classLoader;
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        return null;
    }
}
