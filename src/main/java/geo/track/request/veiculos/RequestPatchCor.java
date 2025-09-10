package geo.track.request.veiculos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPatchCor {
    private Integer idVeiculo;
    private String cor;
}
