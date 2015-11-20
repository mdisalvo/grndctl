package com.grndctl.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH, produces = "image/gif")
    public ResponseEntity<UrlResource> error() {
        return new ResponseEntity<>(new UrlResource(CustomErrorController.class.getResource("/404-bg_2x.gif")), HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
