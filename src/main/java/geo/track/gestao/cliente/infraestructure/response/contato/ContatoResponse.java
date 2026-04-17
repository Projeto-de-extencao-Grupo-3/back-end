package geo.track.gestao.cliente.infraestructure.response.contato;

import lombok.Data;

@Data
public class ContatoResponse {
    private Integer idContato;
    private String telefone;
    private String email;
    private String nomeContato;
    private String departamentoContato;
}

