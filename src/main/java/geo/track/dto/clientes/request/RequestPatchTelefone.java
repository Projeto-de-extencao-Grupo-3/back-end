package geo.track.dto.clientes.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Objeto de requisição para atualizar o telefone de um cliente")
public class RequestPatchTelefone {

    @NotNull
    @Schema(description = "ID do cliente que terá o telefone atualizado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer id;

    @NotBlank
    @Size(min = 10, max = 11)
    @Schema(description = "Novo número de telefone do cliente (com DDD)", example = "11987654321", requiredMode = Schema.RequiredMode.REQUIRED)
    String telefone;
}