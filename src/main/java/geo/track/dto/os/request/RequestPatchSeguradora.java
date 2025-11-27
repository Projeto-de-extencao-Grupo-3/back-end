package geo.track.dto.os.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchSeguradora {
    private Integer idOrdem;
    private Integer fkVeiculo;
    private Boolean seguradora;
}
