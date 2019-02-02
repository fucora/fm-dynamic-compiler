package com.fm.compiler.dynamic.java;

import org.apache.commons.io.IOUtils;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class JavaObject extends SimpleJavaFileObject {

    private String source;
    private ByteArrayOutputStream outPutStream;
    private String charset;


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
    }

    public JavaObject(String name, Kind kind) {
        super(URI.create("String:///" + name + kind.extension), kind);
        source = null;
    }


    public JavaObject(File file, Kind kind) {
        this(file, "utf-8", kind);
    }

    public JavaObject(File file, String charset, Kind kind) {
        super(file.toURI(), kind);
        this.charset = charset;
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


}
