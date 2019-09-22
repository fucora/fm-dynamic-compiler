package com.fm.compiler.dynamicbean;

import com.fm.compiler.exceptions.InstanceCreateException;

public interface InstanceCreater {

    <T> T create(Class<T> cls, Object... args) throws InstanceCreateException;

    <T> T create(Class<T> cls) throws InstanceCreateException;

}
