package com.qbk.boca.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * EnableAdminServer 开启admin服务端
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class BocaAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BocaAdminApplication.class,args);
    }
}
