package geo.track.gestao;

import geo.track.controller.swagger.ClienteSwagger;
import geo.track.gestao.entity.Cliente;
import geo.track.dto.clientes.request.RequestPatchEmail;
import geo.track.dto.clientes.request.RequestPatchTelefone;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.dto.clientes.request.RequestPutCliente;
import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.dto.clientes.response.ClienteVeiculoResponse;
import geo.track.gestao.service.cliente.*;
import geo.track.gestao.util.ClientesMapper;
import geo.track.gestao.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController implements ClienteSwagger {
    private final ClienteService CLIENTE_SERVICE;
    private final CadastrarClienteUseCase CADASTRAR_CLIENTE_USECASE;
    private final AlterarEmailClienteUseCase ALTERAR_EMAIL_CLIENTE_USECASE;
    private final AlterarTelefoneClienteUseCase ALTERAR_TELEFONE_CLIENTE_USECASE;
    private final AtualizarClienteUseCase ATUALIZAR_CLIENTE_USECASE;
    private final DeletarClienteUseCase DELETAR_CLIENTE_USECASE;

    @Override
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> findAllClientes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpfCnpj
    ) {
        List<Cliente> clientes = CLIENTE_SERVICE.findClientes(nome, cpfCnpj);

        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<ClienteResponse> response = ClientesMapper.toResponse(clientes);
        return ResponseEntity.status(200).body(response);
    }

    @Override
    @PostMapping
    public ResponseEntity<ClienteResponse> postCliente(@Valid @RequestBody RequestPostCliente cliente) {
        Cliente novoCliente = CADASTRAR_CLIENTE_USECASE.execute(cliente);
        return ResponseEntity.status(201).body(ClientesMapper.toResponse(novoCliente));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable Integer id) {
        Cliente cliente = CLIENTE_SERVICE.findClienteById(id);

        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Override
    @PatchMapping("/email")
    public ResponseEntity<ClienteResponse> patchEmailCliente(@RequestBody @Valid RequestPatchEmail clienteDTO) {
        Cliente cliente = ALTERAR_EMAIL_CLIENTE_USECASE.execute(clienteDTO);
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Override
    @PatchMapping("/telefone")
    public ResponseEntity<ClienteResponse> patchTelefoneCliente(@RequestBody @Valid RequestPatchTelefone clienteDTO) {
        Cliente cliente = ALTERAR_TELEFONE_CLIENTE_USECASE.execute(clienteDTO);
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Override
    @PutMapping()
    public ResponseEntity<ClienteResponse> putCliente(@RequestBody @Valid RequestPutCliente clienteDTO) {
        Cliente cliente = ATUALIZAR_CLIENTE_USECASE.execute(clienteDTO);
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCliente(@PathVariable Integer id) {
        DELETAR_CLIENTE_USECASE.execute(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<ClienteVeiculoResponse> buscarClientePorPlaca(String placa) {
        Cliente cliente = CLIENTE_SERVICE.buscarClientePorPlaca(placa);

        ClienteVeiculoResponse response = ClientesMapper.toResponseVeiculo(cliente, placa);

        return ResponseEntity.status(200).body(response);
    }
}