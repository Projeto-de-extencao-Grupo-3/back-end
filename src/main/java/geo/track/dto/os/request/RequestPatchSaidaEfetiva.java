package geo.track.dto.os.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RequestPatchSaidaEfetiva {
    private Integer idOrdem;
    private Integer fkVeiculo;
    private LocalDate dtSaidaEfeiva;
}
