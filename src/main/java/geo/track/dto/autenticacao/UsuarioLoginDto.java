package geo.track.dto.autenticacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginDto {
    @Schema(description = "CNPJ do usuário", example = "12.345.678/0001-10")
    private String cnpj;
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;
}
