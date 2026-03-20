package geo.track.jornada.request.entrada;

import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RequestAgendamento(
        @NotNull
        @FutureOrPresent
        LocalDate dataEntradaPrevista,

        @NotNull
        Integer fkVeiculo
) implements GetJornada {
    public TipoJornada getTipoJornada() {
        return TipoJornada.AGENDAMENTO;
    }
}
