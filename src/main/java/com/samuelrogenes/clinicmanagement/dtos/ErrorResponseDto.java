package com.samuelrogenes.clinicmanagement.dtos;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@Schema(
        name = "ErroResposta",
        description = "Schema para armazenar informações detalhadas sobre erros"
)
public class ErrorResponseDto {

    @Schema(
            description = "Caminho da API onde ocorreu o erro",
            example = "/api/pacientes"
    )
    private String apiPath;

    @Schema(
            description = "Código de status HTTP do erro",
            example = "404"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Mensagem de erro detalhada",
            example = "Recurso não encontrado"
    )
    private String errorMessage;

    @Schema(
            description = "Data e hora em que o erro ocorreu",
            example = "2023-07-27T14:00:00"
    )
    private LocalDateTime errorTime;

    @Schema(
            description = "Erros de validação específicos dos campos",
            example = "{\"campo1\": \"mensagem de erro\", \"campo2\": \"mensagem de erro\"}"
    )
    private Map<String, String> validationErrors;
}
