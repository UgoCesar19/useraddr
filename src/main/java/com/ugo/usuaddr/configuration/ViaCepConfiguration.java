package com.ugo.usuaddr.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableFeignClients(basePackages = "com.ugo.usuaddr.rest")
@PropertySource("classpath:viacep.properties")
public class ViaCepConfiguration {
}
