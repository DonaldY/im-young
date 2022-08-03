package com.donald.gateway;

import com.donald.gateway.server.GatewayServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Application implements ApplicationListener<ApplicationReadyEvent> {

    private final GatewayServer gatewayServer;

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(Application.class);
        application.setWebApplicationType(WebApplicationType.NONE);

        application.run(args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {

            gatewayServer.start();

        } catch (InterruptedException e) {

            log.error("gateway server start error about interrupt");
        }
    }
}
