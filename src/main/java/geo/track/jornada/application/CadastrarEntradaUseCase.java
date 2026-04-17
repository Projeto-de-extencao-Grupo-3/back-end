package geo.track.jornada.application;

import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;

public interface CadastrarEntradaUseCase {
    RegistroEntrada execute(RegistroEntrada entrada);
}
