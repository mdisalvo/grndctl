/**
 * MIT License
 *
 * Copyright (c) 2017 grndctl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.grndctl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Michael Di Salvo
 */
@Component
@ComponentScan
@EnableCaching
@EnableSwagger2
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class GrndCtl extends SpringBootServletInitializer {

    private static final String PATHS;

    private static final ApiInfo API_INFO;

    static {
        PATHS =
            "/airline.*" +
            "|/aircraftrep.*" +
            "|/charts.*" +
            "|/airline.*" +
            "|/airsigmet.*" +
            "|/combinedwx.*" +
            "|/conversions.*" +
            "|/intlfpvalidator.*" +
            "|/metar.*" +
            "|/navaid.*" +
            "|/notam.*" +
            "|/pirep.*" +
            "|/station.*" +
            "|/taf.*";

        API_INFO = new ApiInfoBuilder()
                .version("1.0")
                .title("grndctl")
                .description("An Aviators API")
                .contact(new Contact("Michael DiSalvo", null, "michael.vincent.disalvo@gmail.com"))
                .build();
    }

    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder app) {
        return app.sources(GrndCtl.class);
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("navaids", "airlines");
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("com.grndctl")
                .useDefaultResponseMessages(false)
                .apiInfo(API_INFO)
                .select()
                .paths(PathSelectors.regex(PATHS))
                .build();
    }

    public static void main(String...args) {
        SpringApplication.run(GrndCtl.class, args);
    }

}
