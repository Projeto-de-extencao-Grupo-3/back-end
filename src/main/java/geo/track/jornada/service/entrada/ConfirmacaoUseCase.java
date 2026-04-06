package geo.track.jornada.service.entrada;

import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.request.entrada.RequestConfirmacao;
import geo.track.jornada.request.entrada.RequestEntrada;

public interface ConfirmacaoUseCase {
    RegistroEntrada execute(Integer fkRegistro, RequestEntrada request);
}