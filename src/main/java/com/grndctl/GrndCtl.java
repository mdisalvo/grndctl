package com.grndctl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by michael on 10/16/15.
 */
@Component
@ComponentScan
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class GrndCtl extends SpringBootServletInitializer {

    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder app) {
        return app.sources(GrndCtl.class);
    }

    public static void main(String...args) {
        SpringApplication.run(GrndCtl.class, args);
    }


}
