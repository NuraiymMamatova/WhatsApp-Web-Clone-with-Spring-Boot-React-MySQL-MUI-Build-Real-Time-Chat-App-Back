package com.whatsappyoutube.exceptions;

import java.time.LocalDateTime;

public class ErrorDetails {

    private String error;

    private String message;

    private LocalDateTime timeStamp;

    public ErrorDetails() {
    }

    public ErrorDetails(String error, String message, LocalDateTime timeStamp) {
        this.error = error;
        this.message = message;
        this.timeStamp = timeStamp;
    }

}
