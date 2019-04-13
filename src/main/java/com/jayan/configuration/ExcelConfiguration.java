package com.jayan.configuration;

import javax.servlet.Filter;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExcelConfiguration {

	@Bean
    public FilterRegistrationBean requestDumperFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        Filter filter = new RequestDumperFilter();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
	
}
