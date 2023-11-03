package com.whatsappyoutube.dto.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponse {

    private String jwt;

    private boolean isAuth;

}
