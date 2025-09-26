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
    public ResponseEntity<Clientes> postCliente(@Valid @RequestBody Clientes body){
        return clientesService.postCliente(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clientes> getClienteById(@PathVariable Integer id){
        return clientesService.findClienteById(id);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Clientes>> getClienteByNome(@RequestParam String nome) {
        return clientesService.findClienteByNome(nome);
    }

    @GetMapping("/cpfCnpj")
    public ResponseEntity<Clientes> getClienteByCpfCnpj(@RequestParam String cpfCnpj) {
        return clientesService.findClienteByCpfCnpj(cpfCnpj);
    }

    @PatchMapping("/email")
    public ResponseEntity<Clientes> patchEmailCliente(@RequestBody RequestPatchEmail clienteDTO) {
        return clientesService.patchEmailCliente(clienteDTO);
    }

    @PatchMapping("/telefone")
    public ResponseEntity<Clientes> patchTelefoneCliente(@RequestBody RequestPatchTelefone clienteDTO) {
        return clientesService.patchTelefoneCliente(clienteDTO);
    }

    @PutMapping()
    public ResponseEntity<Clientes> putCliente(@RequestBody RequestPutCliente clienteDTO) {
        return clientesService.putCliente(clienteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Clientes> removerCliente(@PathVariable Integer id){
        return clientesService.deletar(id);
    }

    public ClientesController(ClientesService clientesService) {
        this.clientesService = clientesService;
    }
}