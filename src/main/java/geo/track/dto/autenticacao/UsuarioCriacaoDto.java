package geo.track.dto.autenticacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioCriacaoDto {
    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(description = "Razao Social", example = "John Doe")
    private String razaoSocial;

    @Email
    @Schema(description = "Email do usuário", example = "john@doe.com")
    private String email;

    @CNPJ
    @Schema(description = "CNPJ do usuário", example = "12.345.678/0001-10")
    private String cnpj;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "Data de criação do usuário", example = "yyyy-MM-dd HH:mm")
    private LocalDateTime dt_criacao;

    @NotBlank
    @Schema(description = "Status do usuário", example = "ATIVO ou INATIVO")
    private String status;

    @Size(min = 6, max = 20)
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;


}
