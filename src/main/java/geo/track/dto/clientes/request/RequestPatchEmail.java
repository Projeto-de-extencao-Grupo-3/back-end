package geo.track.dto.clientes.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RequestPatchEmail {
    Integer id;
    String email;
}
