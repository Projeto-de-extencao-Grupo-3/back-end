package geo.track.dto.os.request;

import lombok.Data;

@Data
public class RequestPatchSeguradora {
    private Integer idOrdem;
    private Integer fkVeiculo;
    private Boolean seguradora;
}
