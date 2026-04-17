package geo.track.jornada.infraestructure.request.entrada;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RequestEntrada(
        @NotBlank
        String responsavel,

        @NotBlank
        String cpf,

        String observacoes,

        @NotNull @Valid
        List<RequestItemEntrada> itensEntrada
) {
}
