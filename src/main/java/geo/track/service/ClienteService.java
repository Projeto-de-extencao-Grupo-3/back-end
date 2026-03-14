package geo.track.service;

import geo.track.domain.Cliente;
import geo.track.domain.Endereco;
import geo.track.domain.Oficina;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.ClienteExceptionMessages;
import geo.track.exception.constraint.message.Domains;
import geo.track.log.Log;
import geo.track.mapper.ClientesMapper;
import geo.track.repository.ClienteRepository;
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

    public Cliente postCliente(RequestPostCliente body){
        log.info("Iniciando criação de novo cliente com CPF/CNPJ: {}", body.getCpfCnpj());
        if(CLIENTE_REPOSITORY.existsByCpfCnpj(body.getCpfCnpj())){
            log.warn("Falha ao criar cliente: CPF/CNPJ {} já cadastrado", body.getCpfCnpj());
            throw new ConflictException(ClienteExceptionMessages.CPF_EXISTENTE, Domains.CLIENTE);
        }
        Oficina oficina = OFICINA_SERVICE.findOficinasById(body.getFkOficina());
        Endereco endereco = ENDERECO_SERVICE.findEnderecoById(body.getFkEndereco());

        Cliente cliente = ClientesMapper.toEntity(body,oficina, endereco);
        log.info("Cliente criado com sucesso para a oficina ID: {}", body.getFkOficina());
        return CLIENTE_REPOSITORY.save(cliente);
    }

    public List<Cliente> findClientes(){
        log.info("Buscando todos os clientes cadastrados");
        return CLIENTE_REPOSITORY.findAll();
    }

    public Cliente findClienteById(Integer id){
        log.info("Buscando cliente pelo ID: {}", id);
        Optional<Cliente> cliente = CLIENTE_REPOSITORY.findById(id);


        if (cliente.isEmpty()) {
            log.error("Cliente com ID {} não encontrado", id);
            throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID, id), Domains.CLIENTE);
        }

        log.info("Cliente encontrado: {}", cliente.get().getNome());
        return cliente.get();
    }


    public List<Cliente> findClienteByNome(String nome) {
        log.info("Buscando clientes pelo nome contendo: {}", nome);
        List<Cliente> cliente = CLIENTE_REPOSITORY.findByNomeContainingIgnoreCase(nome);

        if (cliente.isEmpty()) {
            log.warn("Nenhum cliente encontrado com o nome: {}", nome);
            throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_NOME, nome), Domains.CLIENTE);
        }
        return cliente;
    }

    public Cliente findClienteByCpfCnpj(String cpfCnpj) {
        log.info("Buscando cliente pelo CPF/CNPJ: {}", cpfCnpj);
        Optional<Cliente> cliente = CLIENTE_REPOSITORY.findByCpfCnpj(cpfCnpj);

        if (cliente.isEmpty()) {
            log.warn("Cliente com CPF/CNPJ {} não encontrado", cpfCnpj);
            throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_CPF_CNPJ, cpfCnpj), Domains.CLIENTE);
        }
        return cliente.get();
    }

    public Cliente patchEmailCliente(RequestPatchEmail body) {
        log.info("Atualizando e-mail do cliente ID: {}", body.getId());
        Optional<Cliente> clientes = CLIENTE_REPOSITORY.findById(body.getId());

        if(clientes.isEmpty()){
            log.error("Falha ao atualizar e-mail: Cliente ID {} não encontrado", body.getId());
            throw new DataNotFoundException(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID_OU_OFICINA, Domains.CLIENTE);
        }
        Cliente cliente = clientes.get();
        cliente.setIdCliente(body.getId());
        cliente.setEmail(body.getEmail());

        log.info("E-mail do cliente ID {} atualizado com sucesso", body.getId());
        return CLIENTE_REPOSITORY.save(cliente);
    }

    public Cliente patchTelefoneCliente(RequestPatchTelefone body) {
        log.info("Atualizando telefone do cliente ID: {}", body.getId());
        Optional<Cliente> clientes = CLIENTE_REPOSITORY.findById(body.getId());

        if(clientes.isEmpty()){
            log.error("Falha ao atualizar telefone: Cliente ID {} não encontrado", body.getId());
            throw new DataNotFoundException(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID_OU_OFICINA, Domains.CLIENTE);
        }

        Cliente cliente = clientes.get();
        cliente.setIdCliente(body.getId());
        cliente.setTelefone(body.getTelefone());

        log.info("Telefone do cliente ID {} atualizado com sucesso", body.getId());
        return CLIENTE_REPOSITORY.save(cliente);
    }

    public Cliente putCliente(RequestPutCliente body) {
        log.info("Atualizando dados completos do cliente ID: {}", body.getIdCliente());
        Optional<Cliente> clientes = CLIENTE_REPOSITORY.findById(body.getIdCliente());


        if(clientes.isEmpty()){
            log.error("Falha na atualização completa: Cliente ID {} não encontrado", body.getIdCliente());
            throw new DataNotFoundException(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID_OU_OFICINA, Domains.CLIENTE);
        }

            Cliente cliente = clientes.get();

            cliente.setIdCliente(body.getIdCliente());
            cliente.setNome(body.getNome());
            cliente.setCpfCnpj(body.getCpfCnpj());
            cliente.setTelefone(body.getTelefone());
            cliente.setEmail(body.getEmail());

          log.info("Dados do cliente ID {} atualizados com sucesso", body.getIdCliente());
          return CLIENTE_REPOSITORY.save(cliente);


    }

    public void deletar(Integer id){
        log.info("Solicitação para deletar cliente ID: {}", id);
        if (CLIENTE_REPOSITORY.existsById(id)){
            CLIENTE_REPOSITORY.deleteById(id);
            log.info("Cliente ID {} deletado com sucesso", id);
            return;
        }
        log.error("Falha ao deletar: Cliente ID {} não existe", id);
        throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID, id), Domains.CLIENTE);
    }

    public Cliente buscarClientePorPlaca(String placa) {
        log.info("Buscando cliente pelo número de placa: {}", placa);
        return CLIENTE_REPOSITORY.findByVeiculosPlaca(placa).orElseThrow(() -> new DataNotFoundException(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_PLACA, Domains.CLIENTE));
    }
}
