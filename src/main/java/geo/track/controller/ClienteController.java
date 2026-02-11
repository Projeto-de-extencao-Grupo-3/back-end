package geo.track.controller;

import geo.track.controller.swagger.ClienteSwagger;
import geo.track.domain.Cliente;
import geo.track.dto.clientes.request.RequestPatchEmail;
import geo.track.dto.clientes.request.RequestPatchTelefone;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.dto.clientes.request.RequestPutCliente;
import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.mapper.ClientesMapper;
import geo.track.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController implements ClienteSwagger {
    private final ClienteService clienteService;

    @Override
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> findAllClientes() {
        List<Cliente> clientes = clienteService.findClientes();

        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<ClienteResponse> response = ClientesMapper.toResponse(clientes);
        return ResponseEntity.status(200).body(response);
    }

    @Override
    @PostMapping
    public ResponseEntity<ClienteResponse> postCliente(@Valid @RequestBody RequestPostCliente cliente) {
        Cliente novoCliente = clienteService.postCliente(cliente);
        return ResponseEntity.status(201).body(ClientesMapper.toResponse(novoCliente));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable Integer id) {
        Cliente cliente = clienteService.findClienteById(id);

        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Override
    @GetMapping("/nome")
    public ResponseEntity<List<ClienteResponse>> getClienteByNome(@RequestParam String nome) {
        List<Cliente> clientes = clienteService.findClienteByNome(nome);
        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(clientes));
    }

    @Override
    @GetMapping("/cpfCnpj")
    public ResponseEntity<ClienteResponse> getClienteByCpfCnpj(@RequestParam String cpfCnpj) {
        Cliente cliente = clienteService.findClienteByCpfCnpj(cpfCnpj);
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Override
    @PatchMapping("/email")
    public ResponseEntity<ClienteResponse> patchEmailCliente(@RequestBody RequestPatchEmail clienteDTO) {
        Cliente cliente = clienteService.patchEmailCliente(clienteDTO);
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Override
    @PatchMapping("/telefone")
    public ResponseEntity<ClienteResponse> patchTelefoneCliente(@RequestBody RequestPatchTelefone clienteDTO) {
        Cliente cliente = clienteService.patchTelefoneCliente(clienteDTO);
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Override
    @PutMapping()
    public ResponseEntity<ClienteResponse> putCliente(@RequestBody RequestPutCliente clienteDTO) {
        Cliente cliente = clienteService.putCliente(clienteDTO);
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCliente(@PathVariable Integer id) {
        clienteService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}