package geo.track.gestao.cliente.domain.contato;

import geo.track.gestao.cliente.infraestructure.request.contato.RequestPutContato;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Contato;
import geo.track.gestao.cliente.infraestructure.persistence.ContatoRepository;
import geo.track.gestao.cliente.application.contato.AtualizarContatoUseCase;
import geo.track.gestao.cliente.infraestructure.ContatoMapper;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.constraint.message.ContatoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtualizarContato implements AtualizarContatoUseCase {
    private final ContatoRepository CONTATO_REPOSITORY;
    private final ContatoService CONTATO_SERVICE;
    private final Log log;

    @Override
    public Contato execute(Integer idCliente, Integer idContato, RequestPutContato body) {
        log.info("Atualizando contato ID {} do cliente ID {}", idContato, idCliente);

        if (CONTATO_SERVICE.existeEmailPorClienteExcluindoContato(idCliente, idContato, body.getEmail())) {
            throw new ConflictException(ContatoExceptionMessages.EMAIL_JA_CADASTRADO_CLIENTE, Domains.CONTATO);
        }

        if (CONTATO_SERVICE.existeTelefonePorClienteExcluindoContato(idCliente, idContato, body.getTelefone())) {
            throw new ConflictException(ContatoExceptionMessages.TELEFONE_JA_CADASTRADO_CLIENTE, Domains.CONTATO);
        }

        Contato contato = CONTATO_SERVICE.buscarContatoPorId(idCliente, idContato);
        contato = ContatoMapper.toEntityUpdate(contato, body);
        return CONTATO_REPOSITORY.save(contato);
    }
}
