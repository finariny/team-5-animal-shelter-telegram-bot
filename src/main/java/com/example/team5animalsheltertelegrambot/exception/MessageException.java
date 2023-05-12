package com.example.team5animalsheltertelegrambot.exception;

public class MessageException extends RuntimeException {
    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }
}
