package geo.track.jornada.request.entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RequestEntrada(
        @NotBlank
        String responsavel,

        @NotBlank
        String cpf,

        @NotNull
        @PositiveOrZero
        Integer extintor,

        @NotNull
        @PositiveOrZero
        Integer macaco,

        @NotNull
        @PositiveOrZero
        Integer chaveRoda,

        @NotNull
        @PositiveOrZero
        Integer geladeira,

        @NotNull
        @PositiveOrZero
        Integer monitor,

        @NotNull
        @PositiveOrZero
        Integer estepe,

        @NotNull
        @PositiveOrZero
        Integer somDvd,

        @NotNull
        @PositiveOrZero
        Integer caixaFerramentas,

        String observacoes
) {
}
