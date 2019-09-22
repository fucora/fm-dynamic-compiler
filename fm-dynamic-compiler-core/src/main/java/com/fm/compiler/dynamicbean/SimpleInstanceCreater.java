package com.fm.compiler.dynamicbean;

import com.fm.compiler.exceptions.InstanceCreateException;

import java.lang.reflect.Constructor;

public class SimpleInstanceCreater implements InstanceCreater {
    @Override
    public <T> T create(Class<T> cls, Object... args) {
        Class[] argClasses = new Class[args.length];
        for (int i = 0; i < argClasses.length; i++) {
            argClasses[i] = args[i].getClass();
        }
        try {
            Constructor<T> constructor = cls.getConstructor(argClasses);
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new InstanceCreateException("创建实例失败:" + e.getMessage(), e);
        }
    }

    @Override
    public <T> T create(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw new InstanceCreateException("创建实例失败:" + e.getMessage(), e);
        }
    }
}
