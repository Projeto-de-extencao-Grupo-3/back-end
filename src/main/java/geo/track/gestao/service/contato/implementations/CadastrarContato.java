package geo.track.gestao.service.contato.implementations;

import geo.track.dto.contatos.request.RequestPostContato;
import geo.track.gestao.entity.Cliente;
import geo.track.gestao.entity.Contato;
import geo.track.gestao.entity.repository.ContatoRepository;
import geo.track.gestao.service.ClienteService;
import geo.track.gestao.service.ContatoService;
import geo.track.gestao.service.contato.CadastrarContatoUseCase;
import geo.track.gestao.util.ContatoMapper;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.constraint.message.ContatoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarContato implements CadastrarContatoUseCase {
    private final ContatoRepository CONTATO_REPOSITORY;
    private final ContatoService CONTATO_SERVICE;
    private final ClienteService CLIENTE_SERVICE;
    private final Log log;

    @Override
    public Contato execute(Integer idCliente, RequestPostContato body) {
        log.info("Cadastrando contato para cliente ID: {}", idCliente);

        if (CONTATO_SERVICE.existeEmailPorCliente(idCliente, body.getEmail())) {
            throw new ConflictException(ContatoExceptionMessages.EMAIL_JA_CADASTRADO_CLIENTE, Domains.CONTATO);
        }

        if (CONTATO_SERVICE.existeTelefonePorCliente(idCliente, body.getTelefone())) {
            throw new ConflictException(ContatoExceptionMessages.TELEFONE_JA_CADASTRADO_CLIENTE, Domains.CONTATO);
        }

        Cliente cliente = CLIENTE_SERVICE.buscarClientePorId(idCliente);
        Contato contato = ContatoMapper.toEntity(body, cliente);
        return CONTATO_REPOSITORY.save(contato);
    }
}

