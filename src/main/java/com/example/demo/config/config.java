package com.example.demo.config;

import com.example.demo.filter.TokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

public class config {
    @Bean
    public Filter kevinTokenFilter() {
        System.out.println("----------------test token filter-----------------");
        return new TokenFilter();
    }

    @Bean
    public FilterRegistrationBean tokenFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(kevinTokenFilter());
        registration.addUrlPatterns("/shares/stock/*");
//        registration.addInitParameter("exclusions","/shares/stock/login,");
//    	registration.addInitParameter("", "");
        registration.setName("tokenFilter");

        return registration;
    }
}
