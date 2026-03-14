package geo.track.dto.os.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RequestPatchSaidaEfetiva {
    @NotNull
    private Integer idOrdem;
    @NotNull
    private Integer fkVeiculo;
    private LocalDate dataSaidaEfetiva;
}
