package geo.track.dto.os.request;

import lombok.Data;

@Data
public class RequestPatchPagtoRealizado {
    private Integer idOrdem;
    private Integer fkVeiculo;
    private Boolean pagtoRealizado;
}
