package com.cefomar.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix= "jwt.auth.convert")
@Validated
@Getter @Setter
public class JwtConverterProperties {
    private String resourceId;
    private String principalAttribute;
}
