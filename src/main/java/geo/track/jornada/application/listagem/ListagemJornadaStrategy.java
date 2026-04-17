package geo.track.jornada.application.listagem;

import geo.track.jornada.infraestructure.request.ListagemJornadaParams;

public interface ListagemJornadaStrategy {
    Object execute(ListagemJornadaParams request);
}
