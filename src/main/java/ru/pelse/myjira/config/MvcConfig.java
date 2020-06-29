package ru.pelse.myjira.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
//    @Value("${upload.path}")
//    private String uploadPath; //загрузка файлов

    //Страница login обрабатывается Spring Security контроллером по умолчанию
    //контроллер не нужен
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

//    @Override //для загрузки файлов
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //все запросы к серверу по пути /img/** будет перенаправлять...
//        registry.addResourceHandler("/img/**")
//                // ...по пути:
//                .addResourceLocations("file:" + uploadPath + "/");
//        registry.addResourceHandler("/static/**")
//                //ресурсы будут искаться в дереве проекта от корня проекта
//                .addResourceLocations("classpath:/static/");
//    }
}
