package geo.track.jornada.application.controle;

import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.Status;

public interface AtualizarStatusUseCase {
    OrdemDeServico execute(Integer idOrdemServico, Status request);
}
