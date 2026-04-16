package geo.track.gestao.service;

import geo.track.gestao.entity.Contato;
import geo.track.gestao.entity.repository.ContatoRepository;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.ContatoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContatoService {
    private final ContatoRepository CONTATO_REPOSITORY;
    private final ClienteService CLIENTE_SERVICE;
    private final Log log;

    public List<Contato> listarContatosPorCliente(Integer idCliente) {
        log.info("Listando contatos do cliente ID: {}", idCliente);
        CLIENTE_SERVICE.buscarClientePorId(idCliente);
        return CONTATO_REPOSITORY.findAllByFkCliente_IdCliente(idCliente);
    }

    public Contato buscarContatoPorId(Integer idCliente, Integer idContato) {
        log.info("Buscando contato ID {} para o cliente ID {}", idContato, idCliente);
        CLIENTE_SERVICE.buscarClientePorId(idCliente);
        return CONTATO_REPOSITORY.findByIdContatoAndFkCliente_IdCliente(idContato, idCliente)
                .orElseThrow(() -> new DataNotFoundException(ContatoExceptionMessages.CONTATO_NAO_ENCONTRADO, Domains.CONTATO));
    }

    public Boolean existeEmailPorCliente(Integer idCliente, String email) {
        return CONTATO_REPOSITORY.existsByEmailIgnoreCaseAndFkCliente_IdCliente(email, idCliente);
    }

    public Boolean existeTelefonePorCliente(Integer idCliente, String telefone) {
        return CONTATO_REPOSITORY.existsByTelefoneAndFkCliente_IdCliente(telefone, idCliente);
    }

    public Boolean existeEmailPorClienteExcluindoContato(Integer idCliente, Integer idContato, String email) {
        return CONTATO_REPOSITORY.existsByEmailIgnoreCaseAndFkCliente_IdClienteAndIdContatoNot(email, idCliente, idContato);
    }

    public Boolean existeTelefonePorClienteExcluindoContato(Integer idCliente, Integer idContato, String telefone) {
        return CONTATO_REPOSITORY.existsByTelefoneAndFkCliente_IdClienteAndIdContatoNot(telefone, idCliente, idContato);
    }
}

