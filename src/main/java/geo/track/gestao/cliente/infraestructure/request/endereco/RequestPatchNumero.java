package geo.track.gestao.cliente.infraestructure.request.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchNumero {
    @NotNull
    @Schema(description = "ID do Endereço", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) // 2. Detalhes do campo
    Integer id;
    @NotNull
    @Schema(description = "Número do Endereço", example = "130", requiredMode = Schema.RequiredMode.REQUIRED) // 2. Detalhes do campo
    Integer numero;
}
