package geo.track.dto.veiculos.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Objeto de requisição para atualizar (PUT) dados de um veículo")
public class RequestPutVeiculos {

    @NotNull
    @Schema(description = "ID do veículo a ser atualizado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idVeiculo;

    @NotBlank
    @Size(min = 7, max = 7)
    @Schema(description = "Placa do veículo (formato Mercosul ou antigo)", example = "BRA2E19", requiredMode = Schema.RequiredMode.REQUIRED)
    private String placa;

    @NotBlank
    @Schema(description = "Marca do veículo", example = "Volkswagen", requiredMode = Schema.RequiredMode.REQUIRED)
    private String marca;

    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 0)
    @Schema(description = "Ano do modelo do veículo", example = "2023", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer anoModelo;

    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 0)
    @Schema(description = "Ano de fabricação do veículo", example = "2022", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer anoFabricacao;
}