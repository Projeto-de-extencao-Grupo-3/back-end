package geo.track.jornada.service.entrada;

import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;

public interface EntradaEfetivaSemCadastroUseCase {
    RegistroEntrada execute(GetJornada requestA);
}