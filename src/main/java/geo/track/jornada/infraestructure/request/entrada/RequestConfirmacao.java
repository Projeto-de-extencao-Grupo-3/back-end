package geo.track.jornada.infraestructure.request.entrada;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RequestConfirmacao(
        @NotNull
        Integer fkRegistro,

        @Valid
        @NotNull
        RequestEntrada entrada
)  {
}
