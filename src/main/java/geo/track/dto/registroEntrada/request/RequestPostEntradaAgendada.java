package geo.track.dto.registroEntrada.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Schema(description = "Objeto de requisição para criar um novo registro de entrada")
public class RequestPostEntradaAgendada {

    @NotNull
    @Schema(description = "Data prevista para a entrada do veículo", example = "2025-10-27", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dtEntradaPrevista;

    @NotNull
    @Schema(description = "ID do veículo que está dando entrada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer fkVeiculo;
}