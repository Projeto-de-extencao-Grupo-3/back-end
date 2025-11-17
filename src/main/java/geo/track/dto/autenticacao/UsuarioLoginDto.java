package geo.track.dto.autenticacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginDto {
    @Schema(description = "CNPJ do usuário", example = "68.496.284/0001-92")
    private String cnpj;
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;
}
