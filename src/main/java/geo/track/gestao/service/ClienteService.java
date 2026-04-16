package geo.track.gestao.service;

import geo.track.gestao.entity.Cliente;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.ClienteExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.entity.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository CLIENTE_REPOSITORY;
    private final Log log;

    public List<Cliente> listarClientes(String paramNome, String paramCpfCnpj) {
        if (paramNome != null && paramCpfCnpj != null) {
            log.info("Buscando clientes pelo nome contendo: {} e CPF/CNPJ: {}", paramNome, paramCpfCnpj);
            return CLIENTE_REPOSITORY.findByNomeContainingIgnoreCaseAndCpfCnpjContainingIgnoreCaseAndAtivoTrue(paramNome, paramCpfCnpj);
        } else if (paramCpfCnpj != null && !paramCpfCnpj.trim().isEmpty()) {
            log.info("Buscando cliente pelo CPF/CNPJ: {}", paramCpfCnpj);
            return CLIENTE_REPOSITORY.findByCpfCnpjContainingIgnoreCaseAndAtivoTrue(paramCpfCnpj);
        } else if (paramNome != null && !paramNome.trim().isEmpty()) {
            log.info("Buscando clientes pelo nome contendo: {}", paramNome);
            return CLIENTE_REPOSITORY.findByNomeContainingIgnoreCaseAndAtivoTrue(paramNome);
        } else {
            log.info("Buscando todos os clientes cadastrados");
            return CLIENTE_REPOSITORY.findByAtivoTrue();
        }
    }

    public Page<Cliente> listarClientesPaginados(Pageable pageable){
        return CLIENTE_REPOSITORY.findAll(pageable);
    }

    public Cliente bucarClientePorId(Integer id) {
        log.info("Buscando cliente pelo ID: {}", id);
        Optional<Cliente> cliente = CLIENTE_REPOSITORY.findByIdClienteAndAtivoTrue(id);

        if (cliente.isEmpty()) {
            log.error("Cliente com ID {} nao encontrado", id);
            throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID, id), Domains.CLIENTE);
        }

        log.info("Cliente encontrado: {}", cliente.get().getNome());
        return cliente.get();
    }

    public Cliente buscarClientePorPlaca(String placa) {
        log.info("Buscando cliente pelo numero de placa: {}", placa);
        return CLIENTE_REPOSITORY.findByVeiculoPlacaAndAtivoTrue(placa).orElseThrow(() -> new DataNotFoundException(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_PLACA, Domains.CLIENTE));
    }
}
