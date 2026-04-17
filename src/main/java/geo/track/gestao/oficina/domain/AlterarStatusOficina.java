package geo.track.gestao.oficina.domain;

import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.gestao.oficina.infraestructure.persistence.OficinaRepository;
import geo.track.gestao.oficina.application.AlterarStatusOficinaUseCase;
import geo.track.gestao.oficina.infraestructure.request.OficinaPatchStatusDTO;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlterarStatusOficina implements AlterarStatusOficinaUseCase {
    private final OficinaRepository OFICINA_REPOSITORY;
    private final OficinaService OFICINA_SERVICEA;
    private final Log log;

    public Oficina execute(OficinaPatchStatusDTO dto) {
        log.info("Iniciando atualizacao de status para oficina ID: {}", dto.getId());
        Oficina oficina = OFICINA_SERVICEA.buscarOficinaPorId(dto.getId());

        oficina.setStatus(dto.getStatus());
        log.info("Status da oficina ID {} alterado para: {}", dto.getId(), dto.getStatus());
        return OFICINA_REPOSITORY.save(oficina);
    }
}

