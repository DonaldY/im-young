package com.donald.gateway;

import com.donald.gateway.server.GatewayServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

    private final GatewayServer gatewayServer;

    public static void main(String[] args) {

       new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {

        this.gatewayServer.start();
    }
}
