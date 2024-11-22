package cc.renxing.nhscspringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Autowired
//    private UserInterceptor userInterceptor;

    @Autowired
    private VisitCounterInterceptor visitCounterInterceptor;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public WebConfig(SpringTemplateEngine templateEngine) {
        templateEngine.addDialect(new SpringSecurityDialect()); // 添加Spring Security支持
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitCounterInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/**", "/images/**", "/static/**","/upload/**", "/webjars/**");  // 排除静态资源
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将 /uploads/** 路径映射到文件系统的上传目录
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir);
    }
}
