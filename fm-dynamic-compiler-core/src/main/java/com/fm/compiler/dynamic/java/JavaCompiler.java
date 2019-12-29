package com.fm.compiler.dynamic.java;

import com.fm.compiler.dynamic.DynamicCodeCompiler;
import java.io.File;
import java.util.Map;

public class JavaCompiler implements DynamicCodeCompiler {


    private ClassLoader classLoader;
    private DynamicCompiler dynamicCompiler;




    public JavaCompiler(){
        this(Thread.currentThread().getContextClassLoader());
    }

    public JavaCompiler(ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.dynamicCompiler = new DynamicCompiler(getClassLoader());
    }

    @Override
    public Class compile(String sCode, String sName) {
//        StringSource javaFileObject = new StringSource(sName, sCode);
        return compile(new JavaObject(sName, sCode));
    }

    @Override
    public Class compile(File file) {
        return compile(JavaObject.ofFile(file));
    }

    public Class compile(JavaObject javaObject){
        dynamicCompiler.addSource(javaObject);
        Map<String, Class<?>> map = dynamicCompiler.build();
        return map.get(javaObject.getJavaName());
    }




    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        return getClassLoader().loadClass(name);
    }

}
