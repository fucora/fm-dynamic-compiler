package com.fm.compiler.dynamicbean;

public interface DynamicBeanManager {

    <T> T compleAndInstance(CompliteDefinition compliteDefinition);

    <T> T compleAndInstance(CompliteDefinition compliteDefinition, Object... args);

    <T> T getBean(String key);

    void registerBean(String key, Object bean);

    void compleAndRegister(String key, CompliteDefinition compliteDefinition);

    void compleAndRegister(String key, CompliteDefinition compliteDefinition, Object... args);

    void removeBean(String key);

}
