package geo.track.jornada.request.entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RequestEntrada(
        @NotBlank
        String responsavel,

        @NotBlank
        String cpf,

        String observacoes
) {
}
