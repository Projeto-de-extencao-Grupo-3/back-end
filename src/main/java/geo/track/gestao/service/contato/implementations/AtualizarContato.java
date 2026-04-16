package geo.track.gestao.service.contato.implementations;

import geo.track.dto.contatos.request.RequestPutContato;
import geo.track.gestao.entity.Contato;
import geo.track.gestao.entity.repository.ContatoRepository;
import geo.track.gestao.service.ContatoService;
import geo.track.gestao.service.contato.AtualizarContatoUseCase;
import geo.track.gestao.util.ContatoMapper;
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
