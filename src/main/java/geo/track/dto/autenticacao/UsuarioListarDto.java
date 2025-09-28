package geo.track.dto.autenticacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioListarDto {
    @Schema(description = "Id do usuário", example = "1")
    private Integer idCliente;

    @Schema(description = "Nome do usuário", example = "John Doe")
    private String nome;

    @Schema(description = "Email do usuário", example = "john@doe.com")
    private String email;

    @Schema(description = "CNPJ do usuário", example = "xx.xxx.xxx/xxxx-xx")
    private String cnpj;

    @Schema(description = "Telefone do usuário", example = "(XX) XXXXX-XXXX")
    private String telefone;

    @Schema(description = "Status do usuário", example = "ATIVO ou INATIVO")
    private String status;

    @Schema(description = "Data de criação do usuário", example = "yyyy-MM-dd HH:mm")
    private LocalDateTime dtCriacao;
}
