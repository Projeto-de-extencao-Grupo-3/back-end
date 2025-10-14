package geo.track.dto.veiculos.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RequestPatchCor {
    private Integer idVeiculo;
    private String cor;
}
