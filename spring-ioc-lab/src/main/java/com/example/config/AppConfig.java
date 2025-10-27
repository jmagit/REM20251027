package com.example.config;

import com.example.model.Saludar;
import com.example.model.Saludo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    Saludo saludoPersonalizado() {
        return new Saludo() {
            @Override
            public String obtenerMensaje() {
                return "Hola desde un Bean configurado manualmente!";
            }
        };
    }

    @Bean
    @Qualifier("saludoGenerico")
    Saludar saludoGenerico() {
        return new Saludar() {
            @Override
            public String obtenerMensaje() {
                return "Hola mundo!";
            }
        };
    }
}
