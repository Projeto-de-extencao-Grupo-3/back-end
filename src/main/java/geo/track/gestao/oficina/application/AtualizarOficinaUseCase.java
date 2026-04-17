package geo.track.gestao.oficina.application;

import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.gestao.oficina.infraestructure.request.RequestPutOficina;

public interface AtualizarOficinaUseCase {
    Oficina execute(RequestPutOficina request);
}

