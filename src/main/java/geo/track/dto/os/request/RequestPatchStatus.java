package geo.track.dto.os.request;

import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchStatus implements GetJornada {
    private Integer idOrdem;
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusVeiculo status;

    @Override
    public TipoJornada getTipoJornada() {
        return TipoJornada.ATUALIZAR_STATUS;
    }
}
