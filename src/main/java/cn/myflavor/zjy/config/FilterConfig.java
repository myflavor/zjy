package cn.myflavor.zjy.config;

import cn.myflavor.zjy.filter.AdminFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Autowired
    AdminFilter adminFilter;

    @Bean
    public FilterRegistrationBean registrationAdminBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(adminFilter);
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.setName("adminFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
