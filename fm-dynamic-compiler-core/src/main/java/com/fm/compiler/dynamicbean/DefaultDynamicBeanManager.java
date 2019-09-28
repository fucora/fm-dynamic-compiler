package com.fm.compiler.dynamicbean;

import com.fm.compiler.CompilerHelper;
import com.fm.compiler.DynamicCompilerHelper;
import com.fm.compiler.InstanceHelper;
import com.fm.compiler.exceptions.InstanceCreateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDynamicBeanManager implements DynamicBeanManager {
    private static final Logger log = LoggerFactory.getLogger(DefaultDynamicBeanManager.class);
    private Map<String, Object> beans = new ConcurrentHashMap<>();

    @Override
    public <T> T compleAndInstance(CompliteDefinition compliteDefinition) {
        return compleAndInstance(compliteDefinition, null);
    }

    @Override
    public <T> T compleAndInstance(CompliteDefinition compliteDefinition, Object... args) {
        try {
            Class<?> cls = compile(compliteDefinition);
            return (T) InstanceHelper.instanceBuilder(cls).instantiation(args);
        } catch (Exception e) {
            log.error("编译失败 -> {}", compliteDefinition);
            throw new InstanceCreateException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String key) {
        return (T) beans.get(key);
    }

    @Override
    public void registerBean(String key, Object bean) {
        beans.put(key, bean);
    }

    @Override
    public void compleAndRegister(String key, CompliteDefinition compliteDefinition) {
        registerBean(key, compleAndInstance(compliteDefinition));
    }

    @Override
    public void compleAndRegister(String key, CompliteDefinition compliteDefinition, Object... args) {
        registerBean(key, compleAndInstance(compliteDefinition, args));
    }

    @Override
    public void removeBean(String key) {
        beans.remove(key);
    }


    private Class<?> compile(CompliteDefinition compliteDefinition) throws Exception {
//        return CompilerHelper.newJavaCompiler().compile(compliteDefinition.getLanguage(), compliteDefinition.getName(), compliteDefinition.getCode());
        return DynamicCompilerHelper.compile(
                compliteDefinition.getLanguage(), compliteDefinition.getName(), compliteDefinition.getCode());
    }

}
