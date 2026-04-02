package geo.track.jornada.service.listagem;

import geo.track.jornada.request.ListagemJornadaParams;

public interface ListagemJornadaStrategy {
    Object execute(ListagemJornadaParams request);
}
