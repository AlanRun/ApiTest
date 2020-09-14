package com.alan.exception;

public class HttpException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public HttpException(String msg) {
        super(msg);
    }

}