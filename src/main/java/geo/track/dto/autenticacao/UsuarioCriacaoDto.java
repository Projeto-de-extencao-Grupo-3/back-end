package geo.track.dto.autenticacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Schema(description = "Razao Social", example = "Geosmar Reformadora")
    private String razaoSocial;

    @Email
    @Schema(description = "Email do usuário", example = "adm@geosmareformadora.com")
    private String email;

    @CNPJ
    @Schema(description = "CNPJ do usuário", example = "68.496.284/0001-92")
    private String cnpj;

    @Schema(description = "Data de criação do usuário", example = "2025-11-16T20:45:00")
    private LocalDateTime dt_criacao;

    @NotNull
    @Schema(description = "Status do usuário", example = "ATIVO")
    private Boolean status;

    @Size(min = 6, max = 20)
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;


}
