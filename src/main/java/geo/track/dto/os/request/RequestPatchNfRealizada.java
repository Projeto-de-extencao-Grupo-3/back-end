package geo.track.dto.os.request;

import lombok.Data;

@Data
public class RequestPatchNfRealizada {
    private Integer idOrdem;
    private Integer fkVeiculo;
    private Boolean nfRealizada;
}
