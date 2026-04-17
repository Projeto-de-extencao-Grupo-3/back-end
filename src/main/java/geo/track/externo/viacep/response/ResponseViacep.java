package geo.track.externo.viacep.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Objeto utilizado para receber o resultado da busca por CEP") // 1. Descrição do objeto
public class ResponseViacep {
    @NotBlank
    @Schema(description = "CEP do Endereço", example = "01414001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cep;

    @NotBlank
    @Schema(description = "Logradouro do Endereço", example = "Rua Haddock Lobo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String logradouro;

    @NotBlank
    @Schema(description = "Bairro do Endereço", example = "Cerqueira César", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bairro;

    @NotBlank
    @Schema(description = "Cidade do Endereço", example = "São Paulo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cidade;

    @NotBlank
    @Schema(description = "Estado do Endereço", example = "São Paulo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String estado;
}
