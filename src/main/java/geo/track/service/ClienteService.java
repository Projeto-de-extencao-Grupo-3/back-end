package geo.track.service;

import geo.track.domain.Cliente;
import geo.track.domain.Endereco;
import geo.track.domain.Oficinas;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.ClienteExceptionMessages;
import geo.track.exception.constraint.message.Domains;
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

    public Cliente postCliente(RequestPostCliente body){
        if(CLIENTE_REPOSITORY.existsByCpfCnpj(body.getCpfCnpj())){
            throw new ConflictException(ClienteExceptionMessages.CPF_EXISTENTE, Domains.CLIENTE);
        }
        Oficinas oficina = OFICINA_SERVICE.findOficinasById(body.getFkOficina());
        Endereco endereco = ENDERECO_SERVICE.findEnderecoById(body.getFkEndereco());

        Cliente cliente = ClientesMapper.toEntity(body,oficina, endereco);
        return CLIENTE_REPOSITORY.save(cliente);
    }

    public List<Cliente> findClientes(){
        return CLIENTE_REPOSITORY.findAll();
    }

    public Cliente findClienteById(Integer id){
        Optional<Cliente> cliente = CLIENTE_REPOSITORY.findById(id);


        if (cliente.isEmpty()) {
            throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID, id), Domains.CLIENTE);
        }

        return cliente.get();
    }


    public List<Cliente> findClienteByNome(String nome) {
        List<Cliente> cliente = CLIENTE_REPOSITORY.findByNomeContainingIgnoreCase(nome);

        if (cliente.isEmpty()) {
            throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_NOME, nome), Domains.CLIENTE);
        }
        return cliente;
    }

    public Cliente findClienteByCpfCnpj(String cpfCnpj) {
        Optional<Cliente> cliente = CLIENTE_REPOSITORY.findByCpfCnpj(cpfCnpj);

        if (cliente.isEmpty()) {
            throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_CPF_CNPJ, cpfCnpj), Domains.CLIENTE);
        }
        return cliente.get();
    }

    public Cliente patchEmailCliente(RequestPatchEmail clienteDTO) {
        Optional<Cliente> clientes = CLIENTE_REPOSITORY.findById(clienteDTO.getId());

        if(clientes.isEmpty()){
            throw new DataNotFoundException(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID_OU_OFICINA, Domains.CLIENTE);
        }
        Cliente cliente = clientes.get();
        cliente.setIdCliente(clienteDTO.getId());
        cliente.setEmail(clienteDTO.getEmail());

        return CLIENTE_REPOSITORY.save(cliente);
    }

    public Cliente patchTelefoneCliente(RequestPatchTelefone clienteDTO) {
        Optional<Cliente> clientes = CLIENTE_REPOSITORY.findById(clienteDTO.getId());

        if(clientes.isEmpty()){
            throw new DataNotFoundException(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID_OU_OFICINA, Domains.CLIENTE);
        }

        Cliente cliente = clientes.get();
        cliente.setIdCliente(clienteDTO.getId());
        cliente.setTelefone(clienteDTO.getTelefone());

        return CLIENTE_REPOSITORY.save(cliente);
    }

    public Cliente putCliente(RequestPutCliente clienteDTO) {
        Optional<Cliente> clientes = CLIENTE_REPOSITORY.findById(clienteDTO.getIdCliente());


        if(clientes.isEmpty()){
            throw new DataNotFoundException(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID_OU_OFICINA, Domains.CLIENTE);
        }

            Cliente cliente = clientes.get();

            cliente.setIdCliente(clienteDTO.getIdCliente());
            cliente.setNome(clienteDTO.getNome());
            cliente.setCpfCnpj(clienteDTO.getCpfCnpj());
            cliente.setTelefone(clienteDTO.getTelefone());
            cliente.setEmail(clienteDTO.getEmail());

          return CLIENTE_REPOSITORY.save(cliente);


    }

    public void deletar(Integer id){
        if (CLIENTE_REPOSITORY.existsById(id)){
            CLIENTE_REPOSITORY.deleteById(id);
            return;
        }
        throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID, id), Domains.CLIENTE);
    }
}
