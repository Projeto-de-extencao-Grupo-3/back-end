package geo.track.dto.clientes.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RequestPatchTelefone {
    Integer id;
    String telefone;
}
