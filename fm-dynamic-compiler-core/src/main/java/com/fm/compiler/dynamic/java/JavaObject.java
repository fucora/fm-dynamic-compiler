package com.fm.compiler.dynamic.java;

import com.fm.compiler.utils.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class JavaObject extends SimpleJavaFileObject {

    private static final char PKG_SEPARATOR = '.';
    private static final char DIR_SEPARATOR = '/';
    private static final String CLASS_FILE_SUFFIX = ".class";
    private static final String JAVA_FILE_SUFFIX = ".java";

    private String source;
    private ByteArrayOutputStream outPutStream;
    private String charset;
    private String javaName;


    /**
     * Construct a SimpleJavaFileObject of the given kind and with the
     * given URI.
     *
     * @param uri  the URI for this file object
     * @param kind the kind of this file object
     */
    protected JavaObject(URI uri, Kind kind) {
        super(uri, kind);
    }


    public JavaObject(String name, String content) {
        super(URI.create("String:///" + name + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
        this.source = content;
        this.javaName = name;
    }

    public JavaObject(String name, Kind kind) {
        super(URI.create("String:///" + name + kind.extension), kind);
        source = null;
        this.javaName = name;
    }


    public JavaObject(File file, Kind kind) {
        this(file, "utf-8", kind);
    }

    public JavaObject(File file, String charset, Kind kind) {
        super(file.toURI(), kind);
        this.charset = charset;

        String className = file.toURI().getPath();
        className = className.replace(DIR_SEPARATOR, PKG_SEPARATOR);
        if(className.endsWith(CLASS_FILE_SUFFIX)){
            className = className.substring(1, className.indexOf(CLASS_FILE_SUFFIX));
        }else if(className.endsWith(JAVA_FILE_SUFFIX)){
            className = className.substring(1, className.indexOf(JAVA_FILE_SUFFIX));
        }

        this.javaName = className;
    }


    public JavaObject(String name, File file, String charset, Kind kind) {
        super(file.toURI(), kind);
        this.charset = charset;
        this.javaName = name;
    }

    public static JavaObject ofFile(File file){
        return ofFile(file, "utf-8");
    }
    public static JavaObject ofFile(File file, String charset){
        String className = file.toURI().getPath();
        className = className.replace(DIR_SEPARATOR, PKG_SEPARATOR);
        if(file.getName().endsWith(JAVA_FILE_SUFFIX)){
            className = className.substring(1, className.indexOf(JAVA_FILE_SUFFIX));
            return new JavaObject(className, file, charset, Kind.SOURCE);
        }else if(file.getName().endsWith(CLASS_FILE_SUFFIX)){
            className = className.substring(1, className.indexOf(CLASS_FILE_SUFFIX));
            return new JavaObject(className, file, charset, Kind.CLASS);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        if (source == null) {
            if (uri != null && this.getKind() == Kind.SOURCE) {
                source = IOUtils.toString(uri, charset);
            }
        }
        if (source == null) {
            throw new IllegalArgumentException("source == null");
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


    public String getJavaName() {
        return javaName;
    }
}
