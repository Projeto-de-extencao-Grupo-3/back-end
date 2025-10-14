package geo.track.dto.os.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestPatchSaidaEfetiva {
    private Integer idOrdem;
    private Integer fkVeiculo;
    private LocalDate dtSaidaEfeiva;
}
