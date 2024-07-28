package com.samuelrogenes.clinicmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@OpenAPIDefinition(
	    info = @Info(
	        title = "Clínica - API de Gerenciamento",
	        description = "Documentação da API REST para o sistema de gerenciamento da Clínica",
	        version = "v1",
	        contact = @Contact(
	            name = "Samuel Rógenes",
	            email = "rogenescarvalho@hotmail.com.br",
	            url = "http://localhost:8080"
	        ),
	        license = @License(
	            name = "Apache 2.0",
	            url = "https://www.apache.org/licenses/LICENSE-2.0"
	        )
	    ),
	    servers = {
	        @Server(
	            url = "http://localhost:8080",
	            description = "Servidor local"
	        ),
	        @Server(
	            url = "https://api.clinicasr.com.br",
	            description = "Servidor de produção"
	        )
	    },
	    externalDocs = @ExternalDocumentation(
	        description = "Documentação completa da API REST da Clínica",
	        url = "http://localhost:8080/swagger-ui.html"
	    )
	)
public class ClinicmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicmanagementApplication.class, args);
	}

}
