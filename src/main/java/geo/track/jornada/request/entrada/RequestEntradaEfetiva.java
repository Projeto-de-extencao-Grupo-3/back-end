package geo.track.jornada.request.entrada;

import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RequestEntradaEfetiva(
        @NotNull
        Integer fkVeiculo,

        @NotNull @Valid
        RequestEntrada entrada
) {
  }
