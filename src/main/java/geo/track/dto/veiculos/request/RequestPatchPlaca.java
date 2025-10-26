package geo.track.dto.veiculos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Objeto de requisição para atualizar a placa de um veículo")
public class RequestPatchPlaca {

    @NotNull
    @Schema(description = "ID do veículo que terá a placa atualizada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idVeiculo;

    @NotBlank
    @Size(min = 7, max = 7)
    @Schema(description = "Nova placa do veículo (formato Mercosul ou antigo)", example = "BRA2E19", requiredMode = Schema.RequiredMode.REQUIRED)
    private String placa;
}