package geo.track.service;

import geo.track.domain.Clientes;
import geo.track.repository.ClientesRepository;
import geo.track.dto.clientes.request.RequestPatchEmail;
import geo.track.dto.clientes.request.RequestPatchTelefone;
import geo.track.dto.clientes.request.RequestPutCliente;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ClientesService {
    private final ClientesRepository clientesRepository;

    public ClientesService(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    public ResponseEntity<Clientes> postCliente(Clientes body){
        Clientes cliente = clientesRepository.save(body);

        return ResponseEntity.status(201).body(cliente);
    }

    public ResponseEntity<Clientes> findClienteById(Integer id){
        Optional<Clientes> cliente = clientesRepository.findById(id);

        if (cliente.isPresent()) {
            return ResponseEntity.status(200).body(cliente.get());
        }
        return ResponseEntity.status(404).body(null);
    }


    public ResponseEntity<List<Clientes>> findClienteByNome(String nome) {
        List<Clientes> clientes = clientesRepository.findByNome(nome);

        if (!clientes.isEmpty()) {
            return ResponseEntity.status(200).body(clientes);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<Clientes> findClienteByCpfCnpj(String cpfCnpj) {
        Optional<Clientes> cliente = clientesRepository.findByCpfCnpj(cpfCnpj);

        if (cliente.isPresent()) {
            return ResponseEntity.status(200).body(cliente.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<Clientes> patchEmailCliente(RequestPatchEmail clienteDTO) {
        Optional<Clientes> clientes = clientesRepository.findById(clienteDTO.getId());

        if (clientes.isPresent()) {
            Clientes cliente = clientes.get();
            cliente.setEmail(clienteDTO.getEmail());
            clientesRepository.save(cliente);
            return ResponseEntity.status(200).body(cliente);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<Clientes> patchTelefoneCliente(RequestPatchTelefone clienteDTO) {
        Optional<Clientes> clientes = clientesRepository.findById(clienteDTO.getId());

        if (clientes.isPresent()) {
            Clientes cliente = clientes.get();
            cliente.setTelefone(clienteDTO.getTelefone());
            clientesRepository.save(cliente);
            return ResponseEntity.status(200).body(cliente);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<Clientes> putCliente(RequestPutCliente clienteDTO) {
        Optional<Clientes> clientes = clientesRepository.findById(clienteDTO.getIdCliente());

        if (clientes.isPresent()) {
            Clientes cliente = clientes.get();

            cliente.setIdCliente(clienteDTO.getIdCliente());
            cliente.setNome(clienteDTO.getNome());
            cliente.setCpfCnpj(clienteDTO.getCpfCnpj());
            cliente.setTelefone(clienteDTO.getTelefone());
            cliente.setEmail(clienteDTO.getEmail());

            clientesRepository.save(cliente);

            return ResponseEntity.status(200).body(cliente);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<Clientes> deletar(Integer id){
        if (clientesRepository.existsById(id)){
            clientesRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}



