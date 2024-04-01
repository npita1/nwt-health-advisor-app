package com.example.accessingdatamysql.exceptions;

public class ForumQuestionNotFoundException extends RuntimeException{
    public ForumQuestionNotFoundException(String message) {
        super(message);
    }

    public ForumQuestionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForumQuestionNotFoundException(Throwable cause) {
        super(cause);
    }
}
