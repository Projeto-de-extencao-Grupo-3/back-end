package geo.track.jornada.request.entrada;

import geo.track.jornada.enums.TipoJornada;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RequestAgendamento(
        @NotNull
        @FutureOrPresent
        LocalDate dataEntradaPrevista,

        @NotNull
        Integer fkVeiculo
) implements GetJornadaType {
    public TipoJornada getTipoJornada() {
        return TipoJornada.AGENDAMENTO;
    }
}
