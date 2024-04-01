package com.liang.config;

import com.liang.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/users/**")
                .addPathPatterns("/stars/**")
                .addPathPatterns("/notes/**")
                .excludePathPatterns("/users/login","/users/register","/users/loginByPhone","/users/updateNameById","/users/updatePwdById");
    }
}
