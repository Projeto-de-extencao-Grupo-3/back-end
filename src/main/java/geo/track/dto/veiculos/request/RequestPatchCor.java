package geo.track.dto.veiculos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Objeto de requisição para atualizar a cor de um veículo")
public class RequestPatchCor {

    @NotNull
    @Schema(description = "ID do veículo que terá a cor atualizada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idVeiculo;

    @NotBlank
    @Schema(description = "Nova cor do veículo", example = "Azul", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cor;
}