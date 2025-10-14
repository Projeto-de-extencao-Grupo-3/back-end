package geo.track.dto.veiculos.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RequestPatchPlaca {
    private Integer idVeiculo;
    private String placa;
}
