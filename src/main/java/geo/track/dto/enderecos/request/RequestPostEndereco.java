package geo.track.dto.enderecos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Objeto utilizado para receber as informações do endereço")
public class RequestPostEndereco {
    @NotBlank
    @Size(max = 8, min = 8)
    @Schema(description = "CEP do Endereço", example = "01414001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cep;

    @NotBlank
    @Schema(description = "Logradouro do Endereço", example = "Rua Haddock Lobo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String logradouro;

    @NotNull
    @Schema(description = "Número do Endereço", example = "595", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer numero;

    @NotBlank
    @Schema(description = "Complemento do Endereço", example = "Melhor faculdade de Tecnologia do Brasil", requiredMode = Schema.RequiredMode.REQUIRED)
    private String complemento;

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
