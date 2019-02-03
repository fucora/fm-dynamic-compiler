package com.fm.compiler;

import com.fm.compiler.dynamic.DynamicCodeCompiler;
import com.fm.compiler.dynamic.groovy.GroovyCompiler;
import com.fm.compiler.dynamic.java.DynaicCompilerContext;
import com.fm.compiler.dynamic.java.JavaCompiler;
import com.fm.compiler.dynamic.java.StoreJavaFileObjectManager;
import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;

public class CompilerHelper {

    public static DynamicCodeCompiler newJavaCompiler() {
        return new JavaCompiler();
    }

    public static DynamicCodeCompiler storeJavaCompiler(File classpath) {
        return new JavaCompiler(DynaicCompilerContext.createContext(
                new StoreJavaFileObjectManager(classpath)));
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

}
