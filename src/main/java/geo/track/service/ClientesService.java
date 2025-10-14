package geo.track.service;

import geo.track.domain.Clientes;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.ClientesRepository;
import geo.track.dto.clientes.request.RequestPatchEmail;
import geo.track.dto.clientes.request.RequestPatchTelefone;
import geo.track.dto.clientes.request.RequestPutCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientesService {
    private final ClientesRepository clientesRepository;

    public Clientes postCliente(Clientes cliente){
        if(clientesRepository.existsByCpfCnpj(cliente.getCpfCnpj())){
            throw new ConflictException("O CPF do cliente informado já existe", "Clientes");
        }
        return clientesRepository.save(cliente);
    }

    public Clientes findClienteById(Integer id){
        Optional<Clientes> cliente = clientesRepository.findById(id);

        if (cliente.isEmpty()) {
            throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Clientes");
        }
        return cliente.get();
    }


    public List<Clientes> findClienteByNome(String nome) {
        List<Clientes> cliente = clientesRepository.findByNome(nome);

        if (cliente.isEmpty()) {
            throw new DataNotFoundException("O nome %d não foi encontrado".formatted(nome), "Clientes");
        }
        return cliente;
    }

    public Clientes findClienteByCpfCnpj(String cpfCnpj) {
        Optional<Clientes> cliente = clientesRepository.findByCpfCnpj(cpfCnpj);

        if (cliente.isEmpty()) {
            throw new DataNotFoundException("CPF %d não foi encontrado".formatted(cpfCnpj), "Clientes");
        }
        return cliente.get();
    }

    public Clientes patchEmailCliente(RequestPatchEmail clienteDTO) {
        Optional<Clientes> clientes = clientesRepository.findById(clienteDTO.getId());

        if(clientes.isEmpty()){
            throw new DataNotFoundException("Não existe cliente com esse ID", "Veiculo");
        }
        Clientes cliente = clientes.get();
        cliente.setIdCliente(clienteDTO.getId());
        cliente.setEmail(clienteDTO.getEmail());

        return clientesRepository.save(cliente);
    }

    public Clientes patchTelefoneCliente(RequestPatchTelefone clienteDTO) {
        Optional<Clientes> clientes = clientesRepository.findById(clienteDTO.getId());

        if(clientes.isEmpty()){
            throw new DataNotFoundException("Não existe cliente com esse ID", "Clientes");
        }

        Clientes cliente = clientes.get();
        cliente.setIdCliente(clienteDTO.getId());
        cliente.setTelefone(clienteDTO.getTelefone());

        return clientesRepository.save(cliente);
    }

    public Clientes putCliente(RequestPutCliente clienteDTO) {
        Optional<Clientes> clientes = clientesRepository.findById(clienteDTO.getIdCliente());


        if(clientes.isEmpty()){
            throw new DataNotFoundException("Não existe cliente com esse ID", "Clientes");
        }

            Clientes cliente = clientes.get();

            cliente.setIdCliente(clienteDTO.getIdCliente());
            cliente.setNome(clienteDTO.getNome());
            cliente.setCpfCnpj(clienteDTO.getCpfCnpj());
            cliente.setTelefone(clienteDTO.getTelefone());
            cliente.setEmail(clienteDTO.getEmail());

          return clientesRepository.save(cliente);


    }

    public Clientes deletar(Integer id){
        if (clientesRepository.existsById(id)){
            clientesRepository.deleteById(id);

        }
        throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Clientes");


    }
}



