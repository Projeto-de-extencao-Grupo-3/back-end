package geo.track.jornada.application.listagem;

import geo.track.jornada.infraestructure.request.ListagemJornadaParams;
import geo.track.jornada.infraestructure.response.listagem.ListagemJornadaResponse;

public interface ListagemAnaliseFinanceiraUseCase extends ListagemJornadaStrategy {
    ListagemJornadaResponse execute(ListagemJornadaParams request);
    }

