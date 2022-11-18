package com.company.pm.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
public class EnvServiceProd implements EnvService{
    @Override
    public String getEnv() {
        return "Production";
    }
}
