package geo.track.jornada.infraestructure.request.entrada;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RequestEntradaEfetiva(
        @NotNull
        Integer fkVeiculo,

        @NotNull @Valid
        RequestEntrada entrada
) {
  }
