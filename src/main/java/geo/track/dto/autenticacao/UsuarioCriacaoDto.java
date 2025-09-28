package geo.track.dto.autenticacao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioCriacaoDto {
    @Size(min = 3, max = 10)
    @Schema(description = "Razao Social", example = "John Doe")
    private String nome;

    @Email
    @Schema(description = "Email do usuário", example = "john@doe.com")
    private String email;

    @Schema(description = "CNPJ do usuário", example = "12.345.678/0001-10")
    private String cnpj;

    @Size(min = 6, max = 20)
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;

    @Schema(description = "Status do usuário", example = "ATIVO ou INATIVO")
    private String status;

    @Schema(description = "Data de criação do usuário", example = "yyyy-MM-dd HH:mm")
    private LocalDateTime dtCriacao;
}
