package geo.track.jornada.request.entrada;

import geo.track.dto.veiculos.request.RequestPostVeiculo;
import geo.track.jornada.enums.TipoJornada;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RequestEntradaEfetivaSemCadastro(
        RequestPostVeiculo veiculo,

        RequestEntrada entrada

) implements GetJornadaType {
    @Override
    public TipoJornada getTipoJornada() {
        return TipoJornada.ENTRADA_EFETIVA_SEM_CADASTRO;
    }
}
