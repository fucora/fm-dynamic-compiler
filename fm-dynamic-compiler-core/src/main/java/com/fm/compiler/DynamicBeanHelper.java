package com.fm.compiler;

import com.fm.compiler.dynamicbean.DefaultDynamicBeanManager;
import com.fm.compiler.dynamicbean.DynamicBeanManager;

public class DynamicBeanHelper {

    static DynamicBeanManager dynamicBeanManager = new DefaultDynamicBeanManager();

    public static DynamicBeanManager getDynamicBeanManager() {
        return dynamicBeanManager;
    }

    public static <T> T getBean(String key){
        return getDynamicBeanManager().getBean(key);
    }
}
