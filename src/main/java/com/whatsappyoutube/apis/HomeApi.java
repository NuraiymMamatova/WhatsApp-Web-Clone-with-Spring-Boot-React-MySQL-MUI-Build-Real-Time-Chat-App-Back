package com.whatsappyoutube.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeApi {

    @GetMapping("/")
    public ResponseEntity<String> homeApi() {
        return new ResponseEntity<String>("Welcome to our watsapp api using spring boot", HttpStatus.OK);
    }

}
