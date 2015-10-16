package com.grndctl.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Michael Di Salvo
 */
@RestController
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String handleError() {
        return "<img src=\"img/404-bg_2x.gif\" width=\"100%\" height=\"100%\"\">";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
