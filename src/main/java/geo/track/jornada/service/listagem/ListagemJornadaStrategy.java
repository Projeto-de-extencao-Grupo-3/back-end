package geo.track.jornada.service.listagem;

import geo.track.jornada.request.ListagemJornadaParams;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;

public interface ListagemJornadaStrategy<T> {
    /**
     * @param request o request que implementa GetJornada, com os dados da entrada
     * @return RegistroEntrada criado ou atualizado
     */
     ListagemJornadaResponse execute(T request);

}
