package geo.track.jornada.infraestructure.request.entrada;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RequestAgendamento(
        @NotNull
        @FutureOrPresent
        LocalDate dataEntradaPrevista,

        @NotNull
        Integer fkVeiculo
) {
}
