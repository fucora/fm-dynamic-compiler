package com.test.fm.java;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    private static Map<String, JavaFileObject> fileObjects = new ConcurrentHashMap<>();


    @Test
    public void test1() {
//        String code = "public class Man {\n" +
//                "\tpublic void hello(){\n" +
//                "\t\tSystem.out.println(\"hello world\");\n" +
//                "\t}\n" +
//                "}";

        String code = "import OApiTransformer;\n" +
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

        System.out.println(code);

//        ClassLoader cl = Thread.currentThread().getContextClassLoader();
//        System.out.println(cl);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
//        JavaFileManager javaFileManager = new MyJavaFileManager(compiler.getStandardFileManager(collector, null, null));
        JavaFileManager javaFileManager = compiler.getStandardFileManager(collector, null, null);

        List<String> options = new ArrayList<>();
        options.add("-target");
        options.add("1.8");

        Pattern CLASS_PATTERN = Pattern.compile("class\\s+([$_a-zA-Z][$_a-zA-Z0-9]*)\\s*");
        Matcher matcher = CLASS_PATTERN.matcher(code);
        String cls;
        if (matcher.find()) {
            cls = matcher.group(1);
        } else {
            throw new IllegalArgumentException("No such class name in " + code);
        }

//        cls = "Tes";
//
////        JavaFileObject javaFileObject = new MyJavaFileObject(cls, code);
//        JavaFileObject javaFileObject = new MyJavaFileObject(
//                new File("/Users/chinadep/IdeaProjects/fm-dynamic-compiler/Tes.java"),
//                JavaFileObject.Kind.SOURCE);
//        Boolean result = compiler.getTask(null, javaFileManager, collector, options, null, Arrays.asList(javaFileObject)).call();
//
//        if(!result){
//            for (Diagnostic diagnostic : collector.getDiagnostics()) {
//                System.out.println(compilePrint(diagnostic));
//            }
//        }
//
//        ClassLoader classloader = new MyClassLoader();
//
//        Class<?> clazz = null;
//        try {
//            clazz = classloader.loadClass(cls);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Method method = null;
//        try {
//            method = clazz.getMethod("hello");
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        try {
//            method.invoke(clazz.newInstance());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
    }



//    @Test
    public void teset2() throws IOException {
        MyJavaFileObject javaFileObject = new MyJavaFileObject(
                new File("/Users/chinadep/IdeaProjects/fm-dynamic-compiler/Tes.class"),
                JavaFileObject.Kind.CLASS);

        javaFileObject.openOutputStream().write(IOUtils.toByteArray(javaFileObject.toUri()));
        ClassLoader classloader = new MyClassLoader();

        String cls = "Tes";
        fileObjects.put("Tes", javaFileObject);

        Class<?> clazz = null;
        try {
            clazz = classloader.loadClass(cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Method method = null;
        try {
            method = clazz.getMethod("hello");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            method.invoke(clazz.newInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }




    public static class MyClassLoader extends ClassLoader {

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            JavaFileObject fileObject = fileObjects.get(name);
            if (fileObject != null) {
                byte[] bytes = ((MyJavaFileObject) fileObject).getCompiledBytes();
                return defineClass(name, bytes, 0, bytes.length);
            }
            try {
                return ClassLoader.getSystemClassLoader().loadClass(name);
            } catch (Exception e) {
                return super.findClass(name);
            }
        }
    }

    public static class MyJavaFileObject extends SimpleJavaFileObject {
        private String source;
        private ByteArrayOutputStream outPutStream;


        public MyJavaFileObject(String name, String source) {
            super(URI.create("String:///" + name + Kind.SOURCE.extension), Kind.SOURCE);
            this.source = source;
        }

        public MyJavaFileObject(String name, Kind kind) {
            super(URI.create("String:///" + name + kind.extension), kind);
            source = null;
        }

        public MyJavaFileObject(File file, Kind kind) {
            super(file.toURI(), kind);
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            if (source == null) {
                source = IOUtils.toString(uri, "utf-8");
                if (source == null) {
                    throw new IllegalArgumentException("source == null");
                }
            }
            return source;
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            outPutStream = new ByteArrayOutputStream();
            return outPutStream;
        }

        public byte[] getCompiledBytes() {
            return outPutStream.toByteArray();
        }
    }

    public static class MyJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        protected MyJavaFileManager(JavaFileManager fileManager) {
            super(fileManager);
        }

        @Override
        public JavaFileObject getJavaFileForInput(Location location, String className, JavaFileObject.Kind kind) throws IOException {
            JavaFileObject javaFileObject = fileObjects.get(className);
            if (javaFileObject == null) {
                super.getJavaFileForInput(location, className, kind);
            }
            return javaFileObject;
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String qualifiedClassName, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            JavaFileObject javaFileObject = new MyJavaFileObject(qualifiedClassName, kind);
            fileObjects.put(qualifiedClassName, javaFileObject);
            return javaFileObject;
        }
    }


    private static String compilePrint(Diagnostic diagnostic) {
        System.out.println("Code:" + diagnostic.getCode());
        System.out.println("Kind:" + diagnostic.getKind());
        System.out.println("Position:" + diagnostic.getPosition());
        System.out.println("Start Position:" + diagnostic.getStartPosition());
        System.out.println("End Position:" + diagnostic.getEndPosition());
        System.out.println("Source:" + diagnostic.getSource());
        System.out.println("Message:" + diagnostic.getMessage(null));
        System.out.println("LineNumber:" + diagnostic.getLineNumber());
        System.out.println("ColumnNumber:" + diagnostic.getColumnNumber());
        StringBuffer res = new StringBuffer();
        res.append("Code:[" + diagnostic.getCode() + "]\n");
        res.append("Kind:[" + diagnostic.getKind() + "]\n");
        res.append("Position:[" + diagnostic.getPosition() + "]\n");
        res.append("Start Position:[" + diagnostic.getStartPosition() + "]\n");
        res.append("End Position:[" + diagnostic.getEndPosition() + "]\n");
        res.append("Source:[" + diagnostic.getSource() + "]\n");
        res.append("Message:[" + diagnostic.getMessage(null) + "]\n");
        res.append("LineNumber:[" + diagnostic.getLineNumber() + "]\n");
        res.append("ColumnNumber:[" + diagnostic.getColumnNumber() + "]\n");
        return res.toString();
    }
}