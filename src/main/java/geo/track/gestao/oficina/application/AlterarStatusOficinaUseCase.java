package geo.track.gestao.oficina.application;

import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.gestao.oficina.infraestructure.request.OficinaPatchStatusDTO;

public interface AlterarStatusOficinaUseCase {
    Oficina execute(OficinaPatchStatusDTO request);
}

