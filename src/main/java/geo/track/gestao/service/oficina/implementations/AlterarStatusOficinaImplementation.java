package geo.track.gestao.service.oficina.implementations;

import geo.track.gestao.entity.Oficina;
import geo.track.gestao.entity.repository.OficinaRepository;
import geo.track.gestao.service.oficina.AlterarStatusOficinaUseCase;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.OficinaExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlterarStatusOficinaImplementation implements AlterarStatusOficinaUseCase {
    private final OficinaRepository OFICINA_REPOSITORY;
    private final Log log;

    public Oficina execute(OficinaPatchStatusDTO dto) {
        log.info("Iniciando atualizacao de status para oficina ID: {}", dto.getId());
        Optional<Oficina> Oficinas = OFICINA_REPOSITORY.findById(dto.getId());

        if (Oficinas.isEmpty()) {
            log.warn("Falha no patch status: Oficina ID {} nao encontrada.", dto.getId());
            throw new DataNotFoundException(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, Domains.OFICINA);
        }

        Oficina emp = Oficinas.get();
        emp.setStatus(dto.getStatus());
        log.info("Status da oficina ID {} alterado para: {}", dto.getId(), dto.getStatus());
        return OFICINA_REPOSITORY.save(emp);
    }
}

