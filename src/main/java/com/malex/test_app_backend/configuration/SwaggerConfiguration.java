package com.malex.test_app_backend.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "JWT-Bearer-Auth",
        type = SecuritySchemeType.HTTP,
        paramName = "Authorization",
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Auth `JWT` token",
        in = SecuritySchemeIn.HEADER)
@SecurityScheme(
        name = "X-Auth-Token-Auth",
        type = SecuritySchemeType.APIKEY,
        paramName = "X-Auth-Token",
        description = "Telegram `tgInitData` data",
        in = SecuritySchemeIn.HEADER)
@Configuration
public class SwaggerConfiguration {}
