package com.fm.compiler.dynamic.java;

import com.fm.compiler.dynamic.exceptions.CompilerException;
import com.fm.compiler.dynamic.DynamicCodeCompiler;
import org.apache.commons.io.IOUtils;

import javax.tools.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaCompiler implements DynamicCodeCompiler {


    private DynaicCompilerContext compilerContext;


    public JavaCompiler(DynaicCompilerContext compilerContext) {
        this.compilerContext = compilerContext;
    }


    public JavaCompiler() {
        this(DynaicCompilerContext.createContext(new MemJavaFileObjectManager()));
    }

    @Override
    public Class compile(String sCode, String sName) throws Exception {
        JavaObject javaFileObject = new JavaObject(sName, sCode);
        return compile(sName, javaFileObject);
    }

    @Override
    public Class compile(File file) throws Exception {
        if (file.getName().endsWith(JavaFileObject.Kind.SOURCE.extension)) {
            JavaObject javaFileObject = new JavaObject(file, JavaFileObject.Kind.SOURCE);
            return compile(getJavaName(file), javaFileObject);
        } else if (file.getName().endsWith(JavaFileObject.Kind.CLASS.extension)) {
            String name = getJavaName(file);
            JavaFileObject javaFileObject = new JavaObject(file, JavaFileObject.Kind.CLASS);
            javaFileObject.openOutputStream().write(IOUtils.toByteArray(javaFileObject.toUri()));
            compilerContext.getJavaObjectManager().putJavaFileObject(name, javaFileObject);
            return compilerContext.getClassLoader().loadClass(name);
        }

        throw new CompilerException("尚未支持编译该类型的文件");
    }

    @Override
    public ClassLoader getClassLoader() {
        return compilerContext.getClassLoader();
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        return getClassLoader().loadClass(name);
    }

    private static List<String> options = new ArrayList<>();

    static {
        options.add("-target");
        options.add("1.8");
    }


    private Class compile(String name, JavaObject javaFileObject) throws CompilerException, ClassNotFoundException {
        CompileContext.initCurrentCompileContext();
        try {
            javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
            Boolean result = compiler.getTask(null, compilerContext.getJavaFileManager(), collector, options, null, Arrays.asList(javaFileObject)).call();
            if (!result) {
                StringBuffer sb = new StringBuffer();
                for (Diagnostic diagnostic : collector.getDiagnostics()) {
                    compilePrint(diagnostic, sb);
                    sb.append("\n");
                }
                throw new CompilerException(sb.toString());
            }
            compilerContext.getJavaObjectManager().compiledJavaObjectHook(javaFileObject);
            return compilerContext.getClassLoader().loadClass(name);
        } finally {
            CompileContext.removeCurrentCompileContext();
        }
    }


    private static void compilePrint(Diagnostic diagnostic, StringBuffer res) {
        res.append("Code:[" + diagnostic.getCode() + "]\n");
        res.append("Kind:[" + diagnostic.getKind() + "]\n");
        res.append("Position:[" + diagnostic.getPosition() + "]\n");
        res.append("Start Position:[" + diagnostic.getStartPosition() + "]\n");
        res.append("End Position:[" + diagnostic.getEndPosition() + "]\n");
        res.append("Source:[" + diagnostic.getSource() + "]\n");
        res.append("Message:[" + diagnostic.getMessage(null) + "]\n");
        res.append("LineNumber:[" + diagnostic.getLineNumber() + "]\n");
        res.append("ColumnNumber:[" + diagnostic.getColumnNumber() + "]\n");
    }


    private String getJavaName(File file) {
        String fileName = file.getName();
        int len = fileName.lastIndexOf(".");
        return fileName.substring(0, len);
    }
}
