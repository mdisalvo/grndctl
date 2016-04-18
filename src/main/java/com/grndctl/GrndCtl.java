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

/**
 *
 * @author Michael Di Salvo
 */
@Component
@ComponentScan
@EnableCaching
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class GrndCtl extends SpringBootServletInitializer {

    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder app) {
        return app.sources(GrndCtl.class);
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("navaids", "airlines", "aircraftreps");
    }

    public static void main(String...args) {
        SpringApplication.run(GrndCtl.class, args);
    }


}
