package geo.track.dto.autenticacao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginDto {
    @NotNull
    @Schema(description = "Email do usuário", example = "geosmar@grotrack.com.br")
    private String email;
    @NotNull
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;
}
