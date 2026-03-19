package geo.track.jornada.request.entrada;

import geo.track.jornada.enums.TipoJornada;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.br.CPF;

public record RequestConfirmacao(
        @NotNull
        Integer fkRegistro,

        @Valid
        @NotNull
        RequestEntrada entrada
) implements GetJornadaType {
    @Override
    public TipoJornada getTipoJornada() {
        return TipoJornada.CONFIRMAR_ENTRADA_AGENDADA;
    }
}
