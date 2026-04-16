package geo.track.dto.contatos.response;

import lombok.Data;

@Data
public class ContatoResponse {
    private Integer idContato;
    private String telefone;
    private String email;
    private String nomeContato;
    private String departamentoContato;
}

