package geo.track.dto.enderecos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RequestPatchNumero {
    @NotNull
    @Schema(description = "ID do Endereço", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) // 2. Detalhes do campo
    Integer id;
    @NotBlank
    @Schema(description = "Número do Endereço", example = "130", requiredMode = Schema.RequiredMode.REQUIRED) // 2. Detalhes do campo
    Integer numero;
}
