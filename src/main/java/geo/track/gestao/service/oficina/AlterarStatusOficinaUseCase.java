package geo.track.gestao.service.oficina;

import geo.track.gestao.entity.Oficina;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;

public interface AlterarStatusOficinaUseCase {
    Oficina execute(OficinaPatchStatusDTO request);
}

