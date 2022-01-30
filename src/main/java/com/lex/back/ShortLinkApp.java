package com.lex.back;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class ShortLinkApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ShortLinkApp.class).web(WebApplicationType.SERVLET).run(args);
    }
}
