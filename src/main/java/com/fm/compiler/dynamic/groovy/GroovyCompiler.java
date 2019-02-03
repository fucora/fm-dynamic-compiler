package com.fm.compiler.dynamic.groovy;

import com.fm.compiler.dynamic.DynamicCodeCompiler;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

public class GroovyCompiler implements DynamicCodeCompiler {

    private static final Logger LOG = LoggerFactory.getLogger(GroovyCompiler.class);

    private CompilerConfiguration compilerConfiguration;
    private GroovyClassLoader groovyClassLoader;


    public GroovyCompiler() {
        this(new CompilerConfiguration());
    }

    public GroovyCompiler(CompilerConfiguration compilerConfiguration) {
        this.compilerConfiguration = compilerConfiguration;
        this.groovyClassLoader = new GroovyClassLoader(this.getClass().getClassLoader(),
                compilerConfiguration);
    }

    /**
     * Compiles Groovy code and returns the Class of the compiles code.
     *
     * @param sCode
     * @param sName
     * @return
     */
    @Override
    public Class compile(String sCode, String sName) {
        GroovyClassLoader loader = getGroovyClassLoader();
        LOG.warn("Compiling filter: " + sName);
        Class groovyClass = loader.parseClass(sCode, sName);
        return groovyClass;
    }

    /**
     * @return a new GroovyClassLoader
     */
    GroovyClassLoader getGroovyClassLoader() {
//        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
//        String classpath = "/Users/saleson/IdeaProjects/fm-dynamic-compiler";
//        configuration.setBytecodePostprocessor((name, ori)->{
//            try {
//                FileUtils.writeByteArrayToFile(new File(classpath, name + ".class"), ori);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            return ori;
//        });
//        configuration.setClasspath(classpath);
//        return new GroovyClassLoader(this.getClass().getClassLoader(),
//                compilerConfiguration);
        return groovyClassLoader;
    }

    /**
     * Compiles groovy class from a file
     *
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public Class compile(File file) throws IOException {
        GroovyClassLoader loader = getGroovyClassLoader();
        Class groovyClass = loader.parseClass(file);
        return groovyClass;
    }

    @Override
    public ClassLoader getClassLoader() {
        return getGroovyClassLoader();
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        return getClassLoader().loadClass(name);
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class UnitTest {
        @Test
        public void testLoadGroovyFromString() {

            GroovyCompiler compiler = spy(new GroovyCompiler(getCompilerConfiguration()));

            long start = System.currentTimeMillis();
            try {

                String code = "class test { public String hello(){System.out.println(\"hello saleson\");return \"hello\"}" +
                        "\n class Td{private String sj;}" +
                        "\n public Td getTd(){return new Td();}" +
                        "} ";
                Class clazz = compiler.compile(code, "test");
                assertNotNull(clazz);
                assertEquals(clazz.getName(), "test");
                GroovyObject groovyObject = (GroovyObject) clazz.newInstance();
                Object[] args = {};
                String s = (String) groovyObject.invokeMethod("hello", args);
                assertEquals(s, "hello");

                Object obj = groovyObject.invokeMethod("getTd", args);
                System.out.println(obj.getClass());

            } catch (Exception e) {
                e.printStackTrace();
                assertFalse(true);
            } finally {
                System.out.println(System.currentTimeMillis() - start);
            }

        }

        @Test
        public void testLoadGroovyFromClass() {

            GroovyCompiler compiler = spy(new GroovyCompiler(getCompilerConfiguration()));

            long start = System.currentTimeMillis();
            try {

                Class clazz = compiler.getGroovyClassLoader().loadClass("test");
                assertNotNull(clazz);
                assertEquals(clazz.getName(), "test");
                GroovyObject groovyObject = (GroovyObject) clazz.newInstance();
                Object[] args = {};
                String s = (String) groovyObject.invokeMethod("hello", args);
                assertEquals(s, "hello");

                Object obj = groovyObject.invokeMethod("getTd", args);
                System.out.println(obj.getClass());
            } catch (Exception e) {
                e.printStackTrace();
                assertFalse(true);
            } finally {
                System.out.println(System.currentTimeMillis() - start);
            }

        }

        private CompilerConfiguration getCompilerConfiguration() {
            CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
            String classpath = "/Users/saleson/IdeaProjects/fm-dynamic-compiler";
            compilerConfiguration.setBytecodePostprocessor((name, ori) -> {
                try {
                    FileUtils.writeByteArrayToFile(new File(classpath, name + ".class"), ori);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return ori;
            });
            compilerConfiguration.setClasspath(classpath);
            return compilerConfiguration;
        }
    }
}