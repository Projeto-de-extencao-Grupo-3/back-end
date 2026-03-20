package geo.track.jornada.request.entrada;

import geo.track.dto.veiculos.request.RequestPostVeiculo;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;

public record RequestEntradaEfetivaSemCadastro(
        RequestPostVeiculo veiculo,

        RequestEntrada entrada

) implements GetJornada {
    @Override
    public TipoJornada getTipoJornada() {
        return TipoJornada.ENTRADA_EFETIVA_SEM_CADASTRO;
    }
}