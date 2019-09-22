package com.fm.compiler.dynamicbean;

public interface Instancer<T> {
    T instantiation(Object... args);
}
