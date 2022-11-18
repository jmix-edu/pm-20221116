package com.company.pm.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("default")
@Service
public class EnvServiceDev implements EnvService{
    @Override
    public String getEnv() {
        return "Development";
    }
}
