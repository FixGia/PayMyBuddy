package com.project.paymybuddy;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@AllArgsConstructor
@EnableSwagger2
public class PayMyBuddyApplication {



    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

    @Bean
    public ViewResolver viewResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setSuffix(".html");

        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(engine);
        return viewResolver;
    }

}

