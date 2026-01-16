package geo.track.service;

import geo.track.domain.Cliente;
import geo.track.domain.Endereco;
import geo.track.domain.Oficinas;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
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
    private final ClienteRepository clientesRepository;
    private final OficinaService oficinaService;
    private final EnderecoService enderecoService;

    public Cliente postCliente(RequestPostCliente body){
        if(clientesRepository.existsByCpfCnpj(body.getCpfCnpj())){throw new ConflictException("O CPF do cliente informado já existe", "Clientes");}
        Oficinas oficina = oficinaService.findOficinasById(body.getFkOficina());
        Endereco endereco = enderecoService.findEnderecoById(body.getFkEndereco());

        Cliente cliente = ClientesMapper.toEntity(body,oficina, endereco);
        return clientesRepository.save(cliente);
    }

    public List<Cliente> findClientes(){
        return clientesRepository.findAll();
    }

    public Cliente findClienteById(Integer id){
        Optional<Cliente> cliente = clientesRepository.findById(id);


        if (cliente.isEmpty()) {
            throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Clientes");
        }

        return cliente.get();
    }


    public List<Cliente> findClienteByNome(String nome) {
        List<Cliente> cliente = clientesRepository.findByNomeContainingIgnoreCase(nome);

        if (cliente.isEmpty()) {
            throw new DataNotFoundException("O nome %s não foi encontrado".formatted(nome), "Clientes");
        }
        return cliente;
    }

    public Cliente findClienteByCpfCnpj(String cpfCnpj) {
        Optional<Cliente> cliente = clientesRepository.findByCpfCnpj(cpfCnpj);

        if (cliente.isEmpty()) {
            throw new DataNotFoundException("CPF %s não foi encontrado".formatted(cpfCnpj), "Clientes");
        }
        return cliente.get();
    }

    public Cliente patchEmailCliente(RequestPatchEmail clienteDTO) {
        Optional<Cliente> clientes = clientesRepository.findById(clienteDTO.getId());

        if(clientes.isEmpty()){
            throw new DataNotFoundException("Não existe cliente com esse ID", "Veiculo");
        }
        Cliente cliente = clientes.get();
        cliente.setIdCliente(clienteDTO.getId());
        cliente.setEmail(clienteDTO.getEmail());

        return clientesRepository.save(cliente);
    }

    public Cliente patchTelefoneCliente(RequestPatchTelefone clienteDTO) {
        Optional<Cliente> clientes = clientesRepository.findById(clienteDTO.getId());

        if(clientes.isEmpty()){
            throw new DataNotFoundException("Não existe cliente com esse ID", "Clientes");
        }

        Cliente cliente = clientes.get();
        cliente.setIdCliente(clienteDTO.getId());
        cliente.setTelefone(clienteDTO.getTelefone());

        return clientesRepository.save(cliente);
    }

    public Cliente putCliente(RequestPutCliente clienteDTO) {
        Optional<Cliente> clientes = clientesRepository.findById(clienteDTO.getIdCliente());


        if(clientes.isEmpty()){
            throw new DataNotFoundException("Não existe cliente com esse ID", "Clientes");
        }

            Cliente cliente = clientes.get();

            cliente.setIdCliente(clienteDTO.getIdCliente());
            cliente.setNome(clienteDTO.getNome());
            cliente.setCpfCnpj(clienteDTO.getCpfCnpj());
            cliente.setTelefone(clienteDTO.getTelefone());
            cliente.setEmail(clienteDTO.getEmail());

          return clientesRepository.save(cliente);


    }

    public void deletar(Integer id){
        if (clientesRepository.existsById(id)){
            clientesRepository.deleteById(id);
            return;
        }
        throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Clientes");
    }
}



