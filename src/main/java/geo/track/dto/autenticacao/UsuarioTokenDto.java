package geo.track.dto.autenticacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioTokenDto {
    private Integer idCliente;
    private String nome;
    private String cnpj;
    private String token;
}
