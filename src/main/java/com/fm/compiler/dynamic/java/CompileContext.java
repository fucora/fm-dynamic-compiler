package com.fm.compiler.dynamic.java;

import java.util.ArrayList;
import java.util.List;

public class CompileContext {

    private static ThreadLocal<CompileContext> cur = new ThreadLocal<>();

    public static CompileContext currentCompileContext() {
        return cur.get();
    }

    static void initCurrentCompileContext() {
        cur.set(new CompileContext());
    }

    static void removeCurrentCompileContext() {
        cur.remove();
    }

    private CompileContext() {

    }


    private List<JavaObject> compiledObjects = new ArrayList<>();

    public void addCompiledObject(JavaObject javaObject) {
        compiledObjects.add(javaObject);
    }


    public List<JavaObject> getCompiledObjects() {
        return compiledObjects;
    }


}
