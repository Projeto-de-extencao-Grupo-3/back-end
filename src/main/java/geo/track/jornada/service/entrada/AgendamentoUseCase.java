package geo.track.jornada.service.entrada;

import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.request.ListagemJornadaParams;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;

public interface AgendamentoUseCase {
    RegistroEntrada execute(GetJornada request);
}