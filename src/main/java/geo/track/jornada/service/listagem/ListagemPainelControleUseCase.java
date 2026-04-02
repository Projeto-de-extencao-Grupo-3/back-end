package geo.track.jornada.service.listagem;

import geo.track.jornada.request.ListagemJornadaParams;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;

public interface ListagemPainelControleUseCase extends ListagemJornadaStrategy {
    ListagemJornadaResponse execute(ListagemJornadaParams request);
    }

