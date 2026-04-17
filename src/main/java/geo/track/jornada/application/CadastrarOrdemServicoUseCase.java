package geo.track.jornada.application;

import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;

public interface CadastrarOrdemServicoUseCase {
    OrdemDeServico execute(Status status);
}
