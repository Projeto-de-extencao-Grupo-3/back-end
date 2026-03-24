package geo.track.service;

import geo.track.gestao.entity.Cliente;
import geo.track.gestao.entity.Endereco;
import geo.track.gestao.entity.Oficina;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.infraestructure.annotation.ToRefactor;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.ClienteExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.util.ClientesMapper;
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
    private final OficinaService OFICINA_SERVICE;
    private final EnderecoService ENDERECO_SERVICE;
    private final Log log;

    @ToRefactor
    public Cliente postCliente(RequestPostCliente body) {
        log.info("Iniciando criação de novo cliente com CPF/CNPJ: {}", body.getCpfCnpj());
        if (CLIENTE_REPOSITORY.existsByCpfCnpj(body.getCpfCnpj())) {
            log.warn("Falha ao criar cliente: CPF/CNPJ {} já cadastrado", body.getCpfCnpj());
            throw new ConflictException(ClienteExceptionMessages.CPF_EXISTENTE, Domains.CLIENTE);
        }
        Oficina oficina = OFICINA_SERVICE.findOficinasById(body.getFkOficina());
        Endereco endereco = ENDERECO_SERVICE.findEnderecoById(body.getFkEndereco());

        Cliente cliente = ClientesMapper.toEntity(body, oficina, endereco);
        log.info("Cliente criado com sucesso para a oficina ID: {}", body.getFkOficina());
        return CLIENTE_REPOSITORY.save(cliente);
    }

    public List<Cliente> findClientes(String nome, String cpfCnpj) {
        if (nome != null && cpfCnpj != null) {
            log.info("Buscando clientes pelo nome contendo: {} e CPF/CNPJ: {}", nome, cpfCnpj);
            List<Cliente> clientes = CLIENTE_REPOSITORY.findByNomeContainingIgnoreCaseAndCpfCnpjContainingIgnoreCase(nome, cpfCnpj);
            return clientes;
        } else if (cpfCnpj != null && !cpfCnpj.trim().isEmpty()) {
            log.info("Buscando cliente pelo CPF/CNPJ: {}", cpfCnpj);
            List<Cliente> cliente = CLIENTE_REPOSITORY.findByCpfCnpjContainingIgnoreCase(cpfCnpj);
        } else if (nome != null && !nome.trim().isEmpty()) {
            log.info("Buscando clientes pelo nome contendo: {}", nome);
            List<Cliente> clientes = CLIENTE_REPOSITORY.findByNomeContainingIgnoreCase(nome);
            return clientes;
        } else {
            log.info("Buscando todos os clientes cadastrados");
            return CLIENTE_REPOSITORY.findAll();
        }

        log.info("Buscando todos os clientes cadastrados");
        return CLIENTE_REPOSITORY.findAll();
    }

    public Cliente findClienteById(Integer id) {
        log.info("Buscando cliente pelo ID: {}", id);
        Optional<Cliente> cliente = CLIENTE_REPOSITORY.findById(id);


        if (cliente.isEmpty()) {
            log.error("Cliente com ID {} não encontrado", id);
            throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID, id), Domains.CLIENTE);
        }

        log.info("Cliente encontrado: {}", cliente.get().getNome());
        return cliente.get();
    }

    @ToRefactor
    public Cliente patchEmailCliente(RequestPatchEmail body) {
        log.info("Atualizando e-mail do cliente ID: {}", body.getIdCliente());
        Cliente cliente = this.findClienteById(body.getIdCliente());
        cliente.setIdCliente(body.getIdCliente());
        cliente.setEmail(body.getEmail());

        log.info("E-mail do cliente ID {} atualizado com sucesso", body.getIdCliente());
        return CLIENTE_REPOSITORY.save(cliente);
    }

    @ToRefactor
    public Cliente patchTelefoneCliente(RequestPatchTelefone body) {
        log.info("Atualizando telefone do cliente ID: {}", body.getIdCliente());
        Cliente cliente = this.findClienteById(body.getIdCliente());
        cliente.setIdCliente(body.getIdCliente());
        cliente.setTelefone(body.getTelefone());

        log.info("Telefone do cliente ID {} atualizado com sucesso", body.getIdCliente());
        return CLIENTE_REPOSITORY.save(cliente);
    }

    @ToRefactor
    public Cliente putCliente(RequestPutCliente body) {
        log.info("Atualizando dados completos do cliente ID: {}", body.getIdCliente());

        Cliente cliente = this.findClienteById(body.getIdCliente());

        cliente.setIdCliente(body.getIdCliente());
        cliente.setNome(body.getNome());
        cliente.setCpfCnpj(body.getCpfCnpj());
        cliente.setTelefone(body.getTelefone());
        cliente.setEmail(body.getEmail());

        log.info("Dados do cliente ID {} atualizados com sucesso", body.getIdCliente());
        return CLIENTE_REPOSITORY.save(cliente);


    }

    @ToRefactor
    public void deletar(Integer id) {
        log.info("Solicitação para deletar cliente ID: {}", id);
        if (CLIENTE_REPOSITORY.existsById(id)) {
            CLIENTE_REPOSITORY.deleteById(id);
            log.info("Cliente ID {} deletado com sucesso", id);
            return;
        }
        log.error("Falha ao deletar: Cliente ID {} não existe", id);
        throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID, id), Domains.CLIENTE);
    }

    public Cliente buscarClientePorPlaca(String placa) {
        log.info("Buscando cliente pelo número de placa: {}", placa);
        return CLIENTE_REPOSITORY.findByVeiculoPlaca(placa).orElseThrow(() -> new DataNotFoundException(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_PLACA, Domains.CLIENTE));
    }
}
