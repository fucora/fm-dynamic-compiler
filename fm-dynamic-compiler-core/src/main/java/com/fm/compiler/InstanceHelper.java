package com.fm.compiler;

import com.fm.compiler.dynamicbean.Instancer;
import com.fm.compiler.dynamicbean.InstanceCreater;
import com.fm.compiler.dynamicbean.SimpleInstanceCreater;
import com.fm.compiler.dynamicbean.SimpleInstancer;

public class InstanceHelper {

    static InstanceCreater instanceCreater = new SimpleInstanceCreater();


    public static InstanceCreater getInstanceCreater() {
        return instanceCreater;
    }


    public static <T> Instancer<T> instanceBuilder(Class<T> cls) {
        return new SimpleInstancer(cls);
    }

}
