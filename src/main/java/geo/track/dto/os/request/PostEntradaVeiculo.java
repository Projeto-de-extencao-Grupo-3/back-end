package geo.track.dto.os.request;

import geo.track.enums.os.StatusVeiculo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostEntradaVeiculo {
    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;
    private Double valorTotal = 0.0;
    private Integer fkEntrada;
}
