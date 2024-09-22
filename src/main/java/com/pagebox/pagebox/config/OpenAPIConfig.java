package com.pagebox.pagebox.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Documentação da API - PageBox", description = "API para gerenciamento de diretórios e arquivos", version = "1.0.0", contact = @Contact(name = "Rockgustavo", url = "https://github.com/rockgustavo", email = "grcuritiba@gmail.com")))
public class OpenAPIConfig {
}
