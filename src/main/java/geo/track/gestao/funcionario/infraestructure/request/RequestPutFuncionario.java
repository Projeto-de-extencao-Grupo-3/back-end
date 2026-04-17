package geo.track.gestao.funcionario.infraestructure.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestPutFuncionario {
    @NotNull
    private Integer id;
    private String nome;
    private String cargo;
    private String especialidade;
    private String telefone;
    private String senha;
    private String email;
}
