package geo.track.dto.veiculos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPatchPlaca {
    private Integer idVeiculo;
    private String placa;
}
