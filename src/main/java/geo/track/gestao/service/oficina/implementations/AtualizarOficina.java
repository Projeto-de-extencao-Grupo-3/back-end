package geo.track.gestao.service.oficina.implementations;

import geo.track.gestao.entity.Oficina;
import geo.track.gestao.entity.repository.OficinaRepository;
import geo.track.gestao.service.oficina.AtualizarOficinaUseCase;
import geo.track.gestao.util.OficinaMapper;
import geo.track.dto.oficinas.request.RequestPutOficina;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.OficinaExceptionMessages;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.service.OficinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtualizarOficina implements AtualizarOficinaUseCase {
    private final OficinaRepository OFICINA_REPOSITORY;
    private final OficinaService OFICINA_SERVICE;
    private final Log log;

    public Oficina execute(RequestPutOficina body) {
        log.info("Iniciando atualizacao completa da oficina ID: {}", body.getIdOficina());
        if (!OFICINA_REPOSITORY.existsById(body.getIdOficina())) {
            log.error("Impossivel atualizar: Oficina ID {} inexistente.", body.getIdOficina());
            throw new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, body.getIdOficina()), Domains.OFICINA);
        }

        Oficina existente = OFICINA_SERVICE.findOficinasById(body.getIdOficina());
        Oficina atualizada = OficinaMapper.toEntityUpdate(existente, body);

        atualizada = OFICINA_REPOSITORY.save(atualizada);
        log.info("Dados da oficina ID {} atualizados com sucesso.", body.getIdOficina());
        return atualizada;
    }
}

