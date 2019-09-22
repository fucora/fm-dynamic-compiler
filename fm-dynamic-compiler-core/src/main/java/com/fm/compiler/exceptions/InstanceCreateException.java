package com.fm.compiler.exceptions;

public class InstanceCreateException extends RuntimeException{

    public InstanceCreateException(String msg){
        super(msg);
    }

    public InstanceCreateException(String msg, Throwable t){
        super(msg, t);
    }

    public InstanceCreateException(Throwable t){
        super(t);
    }

}
