package geo.track.dto.clientes.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Objeto de requisição para atualizar o e-mail de um cliente")
public class RequestPatchEmail {

    @NotNull
    @Schema(description = "ID do cliente que terá o e-mail atualizado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer id;

    @NotBlank
    @Email
    @Schema(description = "Novo endereço de e-mail do cliente", example = "joao.novo@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    String email;
}