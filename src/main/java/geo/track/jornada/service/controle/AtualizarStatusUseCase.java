package geo.track.jornada.service.controle;

import geo.track.dto.os.request.RequestPatchStatus;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.enums.Status;

public interface AtualizarStatusUseCase {
    OrdemDeServico execute(Integer idOrdemServico, Status request);
}
