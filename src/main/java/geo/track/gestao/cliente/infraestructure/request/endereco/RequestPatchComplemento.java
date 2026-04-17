package geo.track.gestao.cliente.infraestructure.request.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchComplemento {
    @NotNull
    @Schema(description = "ID do Endereço", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) // 2. Detalhes do campo
    Integer idEndereco;
    @NotBlank
    @Schema(description = "Complemento do Endereço", example = "Bloco 7b Apartamento 9", requiredMode = Schema.RequiredMode.REQUIRED) // 2. Detalhes do campo
    String complemento;
}
