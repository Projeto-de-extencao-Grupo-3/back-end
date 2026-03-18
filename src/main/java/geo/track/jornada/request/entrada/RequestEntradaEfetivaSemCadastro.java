package geo.track.jornada.request.entrada;

import geo.track.jornada.enums.TipoJornada;

public record RequestEntradaEfetivaSemCadastro() implements GetJornadaType {
    @Override
    public TipoJornada getTipoJornada() {
        return TipoJornada.ENTRADA_SEM_CADASTRO_EFETIVA;
    }
}
