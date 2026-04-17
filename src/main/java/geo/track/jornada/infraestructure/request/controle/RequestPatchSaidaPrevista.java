package geo.track.jornada.infraestructure.request.controle;

import geo.track.jornada.application.TipoJornada;
import geo.track.jornada.application.interfaces.GetJornada;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;

public record RequestPatchSaidaPrevista(
        @FutureOrPresent
        LocalDate saidaPrevista
) implements GetJornada {
    @Override
    public TipoJornada getTipoJornada() {
        return TipoJornada.DEFINIR_SAIDA_PREVISTA;
    }
}
