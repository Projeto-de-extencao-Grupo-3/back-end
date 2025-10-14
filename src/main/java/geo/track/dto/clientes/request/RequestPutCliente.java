package geo.track.dto.clientes.request;

import lombok.Data;
import lombok.Getter;

@Data
public class RequestPutCliente {
    private Integer idCliente;
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String email;
}
