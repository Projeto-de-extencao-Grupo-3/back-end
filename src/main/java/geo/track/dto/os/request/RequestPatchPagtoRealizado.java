package geo.track.dto.os.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchPagtoRealizado {
    private Integer idOrdem;
    private Integer fkVeiculo;
    private Boolean pagtoRealizado;
}
