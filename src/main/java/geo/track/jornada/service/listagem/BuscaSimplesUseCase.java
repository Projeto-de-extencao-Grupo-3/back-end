package geo.track.jornada.service.listagem;

import geo.track.jornada.response.listagem.ListagemJornadaResponse;

public interface BuscaSimplesUseCase {
    ListagemJornadaResponse execute(Integer id);
    }

