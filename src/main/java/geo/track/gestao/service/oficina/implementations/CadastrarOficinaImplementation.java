package geo.track.gestao.service.oficina.implementations;

import geo.track.gestao.entity.Oficina;
import geo.track.gestao.entity.repository.OficinaRepository;
import geo.track.gestao.service.oficina.CadastrarOficinaUseCase;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.OficinaExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarOficinaImplementation implements CadastrarOficinaUseCase {
    private final OficinaRepository OFICINA_REPOSITORY;
    private final Log log;

    public Oficina execute(Oficina body) {
        log.info("Iniciando persistencia de nova oficina: {}", body.getRazaoSocial());
        if (OFICINA_REPOSITORY.findByCnpj(body.getCnpj()).isPresent()) {
            log.error("Falha ao cadastrar: CNPJ {} ja existe no sistema.", body.getCnpj());
            throw new ConflictException(String.format(OficinaExceptionMessages.CNPJ_JA_CADASTRADO, body.getCnpj()), Domains.OFICINA);
        }
        Oficina salva = OFICINA_REPOSITORY.save(body);
        log.info("Oficina salva com sucesso. ID gerado: {}", salva.getIdOficina());
        return salva;
    }
}

