package geo.track.jornada.request.entrada;

import geo.track.jornada.enums.TipoJornada;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RequestEntradaEfetiva(
        @NotNull
        Integer veiculo,

        @NotNull @Valid
        RequestEntrada entrada
) implements GetJornadaType {
    @Override
    public TipoJornada getTipoJornada() {
        return TipoJornada.ENTRADA_EFETIVA;
    }
}
