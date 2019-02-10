package com.fm.compiler.dynamic.exceptions;


public class CompilerException extends Exception {

    public CompilerException(String message) {
        super(message);
    }

    public CompilerException(String message, Throwable cause) {
        super(message, cause);
    }
}
