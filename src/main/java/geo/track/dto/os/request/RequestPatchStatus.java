package geo.track.dto.os.request;

import geo.track.enums.os.StatusVeiculo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPatchStatus {
    private Integer idOrdem;
    private StatusVeiculo status;
    private Integer fkEntrada;
}
