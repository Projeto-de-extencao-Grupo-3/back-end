package geo.track.jornada.infraestructure.request.entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestItemEntrada(
        @NotBlank
        String nomeItem,
        @NotNull
        Integer quantidade
) {
}
