package geo.track.gestao.oficina.application;

import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;

public interface CadastrarOficinaUseCase {
    Oficina execute(Oficina request);
}

