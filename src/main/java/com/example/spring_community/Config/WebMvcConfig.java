package com.example.spring_community.Config;

import com.example.spring_community.Auth.jwt.JwtAuthFilter;
import com.example.spring_community.Auth.jwt.JwtUtils;
import com.example.spring_community.Web.resolver.AuthUserArgumentResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthUserArgumentResolver authUserArgumentResolver;
    private final JwtUtils jwtUtils;

    public WebMvcConfig(AuthUserArgumentResolver authUserArgumentResolver, JwtUtils jwtUtils) {
        this.authUserArgumentResolver = authUserArgumentResolver;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authUserArgumentResolver);
    }

    private static final String IMAGE_RESOURCE_LOCATION =
            "file:/Users/yoojiyeon/Desktop/카테부3기/과제/week7/spring-community/images/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations(IMAGE_RESOURCE_LOCATION);
    }

//    @Bean
//    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter() {
//        FilterRegistrationBean<JwtAuthFilter> bean = new FilterRegistrationBean<>();
//        bean.setFilter(new JwtAuthFilter(jwtUtils));
//        bean.setOrder(0);
//        bean.addUrlPatterns("/*");
//        return bean;
//    }

}
