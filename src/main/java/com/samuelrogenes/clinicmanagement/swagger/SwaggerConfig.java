package com.samuelrogenes.clinicmanagement.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;

@Configuration
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
public class SwaggerConfig {

    @Bean
    GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .packagesToScan("com.samuelrogenes.clinicmanagement")
                .addOpenApiCustomizer(openApi -> {
                    Components components = openApi.getComponents();
                    if (components != null) {
                    	components.getSchemas().remove("LocalTime");
                        components.getSchemas().remove("SortObject");
                        components.getSchemas().remove("PageableObject");
                        components.getSchemas().remove("PagePacienteProjection");
                        components.getSchemas().remove("PageMedicoProjection");
                        components.getSchemas().remove("AgendamentoMedicoEntity");
                        components.getSchemas().remove("AgendamentoMedicoProjection");
                        components.getSchemas().remove("PageAgendamentoMedicoProjection");
                    }
                })
                .build();
    }
}
