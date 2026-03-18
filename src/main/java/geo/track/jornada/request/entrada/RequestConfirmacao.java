package geo.track.jornada.request.entrada;

import geo.track.jornada.enums.TipoJornada;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.br.CPF;

public record RequestConfirmacao(
        @NotNull
        Integer fkRegistro,

        @NotBlank
        String responsavel,

        @CPF
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
) implements GetJornadaType {
    @Override
    public TipoJornada getTipoJornada() {
        return TipoJornada.CONFIRMAR_ENTRADA_AGENDADA;
    }
}
