package geo.track.gestao.service.oficina.implementations;

import geo.track.gestao.entity.repository.OficinaRepository;
import geo.track.gestao.service.oficina.DeletarOficinaUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.OficinaExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarOficina implements DeletarOficinaUseCase {
    private final OficinaRepository OFICINA_REPOSITORY;
    private final Log log;

    public void execute(Integer id) {
        log.info("Iniciando exclusao da oficina ID: {}", id);
        if (!OFICINA_REPOSITORY.existsById(id)) {
            log.error("Falha ao remover: Oficina ID {} nao existe.", id);
            throw new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, id), Domains.OFICINA);
        }
        OFICINA_REPOSITORY.deleteById(id);
        log.info("Oficina ID {} excluida com sucesso.", id);
    }
}

