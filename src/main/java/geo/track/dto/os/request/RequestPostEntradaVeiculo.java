package geo.track.dto.os.request;

import geo.track.enums.os.StatusVeiculo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPostEntradaVeiculo {
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;
    @NotNull
    private Integer fkEntrada;
}
