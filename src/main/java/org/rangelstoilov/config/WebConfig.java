package org.rangelstoilov.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LayoutDialect thymeleafLayoutDialect() {
        return  new LayoutDialect();
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogInterceptor());
//        registry.addInterceptor(new OldLoginInterceptor())//
//                .addPathPatterns("/oldLogin");
//    }
}
