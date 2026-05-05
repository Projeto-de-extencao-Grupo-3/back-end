package geo.track.gestao.funcionario.infraestructure.response;

import lombok.Data;

@Data
public class FuncionarioResponse {
    private Integer idFuncionario;
    private String nome;
    private String cargo;
    private String especialidade;
    private String email;
    private String senha;
    private String telefone;
    private Integer idOficina;
    private Boolean possuiLogin;
}
