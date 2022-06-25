package com.donald.sdkandroid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SdkAndroidApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(SdkAndroidApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);

        application.run(args);
    }
}
