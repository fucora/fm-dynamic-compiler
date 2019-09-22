package com.fm.compiler;

import com.fm.compiler.dynamic.DynamicCodeCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DynamicCompilerHelper {

    private static final Logger log = LoggerFactory.getLogger(DynamicCompilerHelper.class);

    private static Map<String, DynamicCodeCompiler> compilers = new HashMap<>();
    static {
        compilers.put("java", CompilerHelper.newJavaCompiler());
        compilers.put("groovy", CompilerHelper.newGroovyCompiler());
    }

    public static Class compile(String language, String name, String code) throws Exception {
        return getDynamicCompiler(language).compile(code, name);
    }

    private static DynamicCodeCompiler getDynamicCompiler(String language) {
        DynamicCodeCompiler compiler = compilers.get(language);
        if (compiler == null) {
            log.warn("没有找到名为'{}'编程语言的动态编译器", language);
            throw new NullPointerException("不支持该编程语言的动态编译:" + language);
        }
        return compiler;
    }

}
