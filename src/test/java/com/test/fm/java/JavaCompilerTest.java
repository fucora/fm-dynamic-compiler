package com.test.fm.java;

import com.fm.data.trade.dynamic.java.DynaicCompilerContext;
import com.fm.data.trade.dynamic.java.JavaCompiler;
import com.fm.data.trade.dynamic.java.JavaFileObjectManager;
import com.fm.data.trade.dynamic.java.MemJavaFileObjectManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;


@RunWith(MockitoJUnitRunner.class)
public class JavaCompilerTest {


    private static JavaCompiler javaCompiler;
    private static JavaFileObjectManager javaFileObjectManager;

    @BeforeClass
    public static void before() {
        System.out.println("before....");
        javaFileObjectManager = new MemJavaFileObjectManager();
        javaCompiler = new JavaCompiler(DynaicCompilerContext.createContext(javaFileObjectManager));
    }


    @Test
    public void test1() throws Exception {
        System.out.println("start test1...");

        String code = "import com.fm.data.trade.dynamic.OApiTransformer;\n" +
                "public class OApiTransformerA extends OApiTransformer {\n" +
                "\tpublic void hello(){\n" +
                "\t\tSystem.out.println(sd());\n" +
                "\t}\n" +
                "\t@Override\n" +
                "    public String sd() {\n" +
                "\n" +
                "        return \"ds\";\n" +
                "    }\n" +
                "}";
        Class cls = javaCompiler.compile(code, "OApiTransformerA");
        Object obj = cls.newInstance();
        Method method = cls.getMethod("hello");
        method.invoke(obj);
        System.out.println("... end test1");
    }


    @Test
    public void test2() throws Exception {
        System.out.println("start test2...");
        Class cls = javaCompiler.compile(new File("/Users/chinadep/IdeaProjects/fm-dynamic-compiler/Tes.java"));
        Object obj = cls.newInstance();
        Method method = cls.getMethod("hello");
        method.invoke(obj);
        System.out.println("... end test2");
    }

    @Test
    public void test3() throws Exception {
        System.out.println("start test3...");
        Class cls = javaCompiler.compile(new File("/Users/chinadep/IdeaProjects/fm-dynamic-compiler/Tes.class"));
        Object obj = cls.newInstance();
        Method method = cls.getMethod("hello");
        method.invoke(obj);
        System.out.println("... end test3");
    }


    @AfterClass
    public static void after() throws Exception{
        Field field = MemJavaFileObjectManager.class.getDeclaredField("fileObjects");
        field.setAccessible(true);
        Map map = (Map) field.get(javaFileObjectManager);
        System.out.println(map);
    }
}
