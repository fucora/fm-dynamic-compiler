package com.fm.data.trade.dynamic.java;

import javax.tools.ToolProvider;

public class DynaicCompilerContext {


    public static DynaicCompilerContext createContext(JavaFileObjectManager javaObjectManager) {
        DynaicCompilerContext compilerContext = new DynaicCompilerContext();
        compilerContext.setJavaObjectManager(javaObjectManager);
        compilerContext.setClassLoader(new DynamicClassLoader(javaObjectManager));
        compilerContext.setJavaFileManager(
                new DynamicJavaFileManager(
                        ToolProvider.getSystemJavaCompiler().getStandardFileManager(
                                null, null, null),
                        javaObjectManager));
        return compilerContext;
    }

    private JavaFileObjectManager javaObjectManager;
    private DynamicClassLoader classLoader;
    private DynamicJavaFileManager javaFileManager;


    public JavaFileObjectManager getJavaObjectManager() {
        return javaObjectManager;
    }

    public void setJavaObjectManager(JavaFileObjectManager javaObjectManager) {
        this.javaObjectManager = javaObjectManager;
    }

    public DynamicClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(DynamicClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public DynamicJavaFileManager getJavaFileManager() {
        return javaFileManager;
    }

    public void setJavaFileManager(DynamicJavaFileManager javaFileManager) {
        this.javaFileManager = javaFileManager;
    }
}
