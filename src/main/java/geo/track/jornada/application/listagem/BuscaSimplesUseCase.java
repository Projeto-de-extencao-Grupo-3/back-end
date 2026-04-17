package geo.track.jornada.application.listagem;

import geo.track.jornada.infraestructure.response.listagem.ListagemJornadaResponse;

public interface BuscaSimplesUseCase {
    ListagemJornadaResponse execute(Integer id);
    }

