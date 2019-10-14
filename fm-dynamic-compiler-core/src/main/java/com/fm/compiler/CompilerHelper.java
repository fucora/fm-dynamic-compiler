package com.fm.compiler;

import com.fm.compiler.dynamic.DynamicCodeCompiler;
import com.fm.compiler.dynamic.groovy.GroovyCompiler;
import com.fm.compiler.dynamic.java.*;
import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class CompilerHelper {


    private static AtomicReference<ClassLoader> javaCompilerClassLoader = new AtomicReference<>();


    public static DynamicCodeCompiler newJavaCompiler() {
        ClassLoader classLoader = getJavaCompilerClassLoader();
        if(Objects.isNull(classLoader)){
            return new JavaCompiler();
        }
        return new JavaCompiler(classLoader);
//        return new JavaArthasCompiler();
    }


    public static DynamicCodeCompiler newGroovyCompiler(){
        return new GroovyCompiler();
    }


    public static DynamicCodeCompiler storeGroovyCompiler(File classpath){
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        String classPath = classpath.getAbsolutePath();
        compilerConfiguration.setBytecodePostprocessor((name, ori) -> {
            try {
                FileUtils.writeByteArrayToFile(new File(classpath, name + ".class"), ori);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ori;
        });
        compilerConfiguration.setClasspath(classPath);
        return new GroovyCompiler();
    }


    public static ClassLoader getJavaCompilerClassLoader(){
        return javaCompilerClassLoader.get();
    }

    public static void registerJavaCompilerClassLoader(ClassLoader classLoader){
        javaCompilerClassLoader.set(classLoader);
    }

}
