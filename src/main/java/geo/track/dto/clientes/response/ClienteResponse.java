package geo.track.dto.clientes.response;

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
    private List<Integer> idEndereco;
}
