package com.fm.compiler.dynamicbean;


import com.fm.compiler.InstanceHelper;

import java.util.Objects;

public class SimpleInstancer<T> implements Instancer<T> {

    private Class<T> cls;


    public SimpleInstancer(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public T instantiation(Object... args) {
        if (Objects.isNull(args) || args.length == 0) {
            return InstanceHelper.getInstanceCreater().create(cls);
        }
        return InstanceHelper.getInstanceCreater().create(cls, args);
    }
}
