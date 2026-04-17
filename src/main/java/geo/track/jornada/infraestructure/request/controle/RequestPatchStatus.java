package geo.track.jornada.infraestructure.request.controle;

import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.application.TipoJornada;
import geo.track.jornada.application.interfaces.GetJornada;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchStatus implements GetJornada {
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @Override
    public TipoJornada getTipoJornada() {
        return TipoJornada.ATUALIZAR_STATUS;
    }
}
