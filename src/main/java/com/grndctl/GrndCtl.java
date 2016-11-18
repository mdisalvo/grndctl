/**
 * This file is part of grndctl.
 *
 * grndctl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * grndctl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with grndctl.  If not, see <http://www.gnu.org/licenses/>.
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

    private static final String PATHS =
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

    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder app) {
        return app.sources(GrndCtl.class);
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("navaids", "airlines", "aircraftreps");
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("com.grndctl")
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex(PATHS))
                .build();
    }

    public static void main(String...args) {
        SpringApplication.run(GrndCtl.class, args);
    }

    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .version("1.0")
                .title("grndctl")
                .description("An Aviators API")
                .contact("Michael Di Salvo (michael.vincent.disalvo@gmail.com)")
                .licenseUrl("https://www.gnu.org/licenses/gpl-3.0.html")
                .build();
    }


}
