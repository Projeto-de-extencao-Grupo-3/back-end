package geo.track.controller;

import geo.track.domain.Clientes;
import geo.track.dto.clientes.request.RequestPatchEmail;
import geo.track.dto.clientes.request.RequestPatchTelefone;
import geo.track.dto.clientes.request.RequestPutCliente;
import geo.track.service.ClientesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientesController {
    private final ClientesService clientesService;

    @PostMapping
    public ResponseEntity<Clientes> postCliente(@Valid @RequestBody Clientes cliente) {
        Clientes clientes = clientesService.postCliente(cliente);
        return ResponseEntity.status(201).body(clientes);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Clientes> getClienteById(@PathVariable Integer id) {
        Clientes cliente = clientesService.findClienteById(id);
        return ResponseEntity.status(200).body(cliente);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Clientes>> getClienteByNome(@RequestParam String nome) {
        List<Clientes> cliente = clientesService.findClienteByNome(nome);
        if(cliente.isEmpty()){
            return ResponseEntity.status(204).body(cliente);
        }
        return ResponseEntity.status(200).body(cliente);
    }

    @GetMapping("/cpfCnpj")
    public ResponseEntity<Clientes> getClienteByCpfCnpj(@RequestParam String cpfCnpj) {
        Clientes cliente = clientesService.findClienteByCpfCnpj(cpfCnpj);
        return ResponseEntity.status(200).body(cliente);
    }

    @PatchMapping("/email")
    public ResponseEntity<Clientes> patchEmailCliente(@RequestBody RequestPatchEmail clienteDTO) {
        Clientes cliente = clientesService.patchEmailCliente(clienteDTO);
        return ResponseEntity.status(200).body(cliente);
    }

    @PatchMapping("/telefone")
    public ResponseEntity<Clientes> patchTelefoneCliente(@RequestBody RequestPatchTelefone clienteDTO) {
        Clientes cliente = clientesService.patchTelefoneCliente(clienteDTO);
        return ResponseEntity.status(200).body(cliente);
    }

    @PutMapping()
    public ResponseEntity<Clientes> putCliente(@RequestBody RequestPutCliente clienteDTO) {
        Clientes cliente = clientesService.putCliente(clienteDTO);
        return ResponseEntity.status(200).body(cliente);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Clientes> removerCliente(@PathVariable Integer id){
         clientesService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    public ClientesController(ClientesService clientesService) {
        this.clientesService = clientesService;
    }
}