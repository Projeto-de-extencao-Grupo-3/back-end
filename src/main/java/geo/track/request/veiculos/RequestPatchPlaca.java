package geo.track.request.veiculos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPatchPlaca {
    private Integer idVeiculo;
    private String placa;
}
