package geo.track.gestao.cliente.infraestructure.response.cliente;

import geo.track.gestao.cliente.infraestructure.response.contato.ContatoResponse;
import geo.track.gestao.cliente.infraestructure.response.endereco.EnderecoResponse;
import lombok.Data;

import java.util.List;

@Data
public class ClienteResponse {
    private Integer idCliente;
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String email;
    private String tipoCliente;
    private Integer idOficina;
    private List<ContatoResponse> meiosContato;
    private List<EnderecoResponse> enderecos;
}
