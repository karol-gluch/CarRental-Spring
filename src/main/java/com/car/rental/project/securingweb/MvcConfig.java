package com.car.rental.project.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/flota").setViewName("flota");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/kontakt").setViewName("kontakt");
        registry.addViewController("/ofirmie").setViewName("ofirmie");
        registry.addViewController("/registration").setViewName("registration");
    }

}
