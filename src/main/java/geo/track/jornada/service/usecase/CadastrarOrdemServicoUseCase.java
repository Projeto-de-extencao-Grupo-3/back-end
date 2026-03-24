package geo.track.jornada.service.usecase;

import geo.track.jornada.enums.Status;
import geo.track.jornada.entity.OrdemDeServico;

public interface CadastrarOrdemServicoUseCase {
    OrdemDeServico execute(Status status);
}
