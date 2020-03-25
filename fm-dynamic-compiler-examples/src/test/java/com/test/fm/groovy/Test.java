package com.test.fm.groovy;

import com.fm.compiler.dynamic.groovy.GroovyCompiler;
import com.fm.data.trade.dynamic.OApiTransformer;

import java.io.File;
import java.io.IOException;

public class Test {

//    @org.junit.Test
    public void test() throws IOException, IllegalAccessException, InstantiationException {
        File file = new File("/Users/saleson/IdeaProjects/fm/huaice/data-trade-dynamic/src/test/java/com/test/fm/groovy/OApiTransformerTest.groovy");
        GroovyCompiler compiler = new GroovyCompiler();
        Class<OApiTransformer> cls = compiler.compile(file);
        OApiTransformer transformer = cls.newInstance();
        String body = transformer.sd();
        System.out.println(body);
    }
}
