package com.ugo.usuaddr.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:datasource-dev.properties")
@Profile({"dev", "test"})
public class DatasourceConfigurationDev {
}