package geo.track.jornada.service.usecase;

import geo.track.jornada.entity.RegistroEntrada;

public interface CadastrarEntradaUseCase {
    RegistroEntrada execute(RegistroEntrada entrada);
}
