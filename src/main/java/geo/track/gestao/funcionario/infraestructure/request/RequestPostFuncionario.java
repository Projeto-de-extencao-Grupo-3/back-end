package geo.track.gestao.funcionario.infraestructure.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestPostFuncionario {

    @NotBlank
    private String nome;
    @NotBlank
    private String cargo;
    @NotBlank
    private String especialidade;
    @NotBlank
    private String telefone;
    @NotBlank
    private String senha;
    @NotBlank
    private String email;
    @NotNull
    private Integer fkOficina;
}
