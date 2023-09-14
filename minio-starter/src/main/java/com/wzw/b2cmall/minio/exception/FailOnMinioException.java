package com.wzw.b2cmall.minio.exception;

public class FailOnMinioException extends RuntimeException{
    public FailOnMinioException() {
        this("minio相关错误,请联系管理员");
    }

    public FailOnMinioException(String s) {
        super(s);
    }
}
