package geo.track.gestao.service;

import geo.track.gestao.entity.Cliente;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.gestao.service.cliente.AlterarEmailClienteUseCase;
import geo.track.gestao.service.cliente.AlterarTelefoneClienteUseCase;
import geo.track.gestao.service.cliente.AtualizarClienteUseCase;
import geo.track.gestao.service.cliente.CadastrarClienteUseCase;
import geo.track.gestao.service.cliente.DeletarClienteUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.ClienteExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.entity.repository.ClienteRepository;
import geo.track.dto.clientes.request.RequestPatchEmail;
import geo.track.dto.clientes.request.RequestPatchTelefone;
import geo.track.dto.clientes.request.RequestPutCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository CLIENTE_REPOSITORY;
    private final Log log;

    public List<Cliente> findClientes(String nome, String cpfCnpj) {
        if (nome != null && cpfCnpj != null) {
            log.info("Buscando clientes pelo nome contendo: {} e CPF/CNPJ: {}", nome, cpfCnpj);
            return CLIENTE_REPOSITORY.findByNomeContainingIgnoreCaseAndCpfCnpjContainingIgnoreCase(nome, cpfCnpj);
        } else if (cpfCnpj != null && !cpfCnpj.trim().isEmpty()) {
            log.info("Buscando cliente pelo CPF/CNPJ: {}", cpfCnpj);
            return CLIENTE_REPOSITORY.findByCpfCnpjContainingIgnoreCase(cpfCnpj);
        } else if (nome != null && !nome.trim().isEmpty()) {
            log.info("Buscando clientes pelo nome contendo: {}", nome);
            return CLIENTE_REPOSITORY.findByNomeContainingIgnoreCase(nome);
        } else {
            log.info("Buscando todos os clientes cadastrados");
            return CLIENTE_REPOSITORY.findAll();
        }
    }

    public Cliente findClienteById(Integer id) {
        log.info("Buscando cliente pelo ID: {}", id);
        Optional<Cliente> cliente = CLIENTE_REPOSITORY.findById(id);

        if (cliente.isEmpty()) {
            log.error("Cliente com ID {} nao encontrado", id);
            throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID, id), Domains.CLIENTE);
        }

        log.info("Cliente encontrado: {}", cliente.get().getNome());
        return cliente.get();
    }

    public Cliente buscarClientePorPlaca(String placa) {
        log.info("Buscando cliente pelo numero de placa: {}", placa);
        return CLIENTE_REPOSITORY.findByVeiculoPlaca(placa).orElseThrow(() -> new DataNotFoundException(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_PLACA, Domains.CLIENTE));
    }
}
