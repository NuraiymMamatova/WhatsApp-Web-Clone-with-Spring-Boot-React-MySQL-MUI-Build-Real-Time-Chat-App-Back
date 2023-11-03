package com.whatsappyoutube.exceptions;

import lombok.NoArgsConstructor;

public class ChatException extends Exception {

    public ChatException(String message) {
        super(message);
    }
}
