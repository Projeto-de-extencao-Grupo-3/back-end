package geo.track.gestao;

import geo.track.controller.swagger.ClienteSwagger;
import geo.track.dto.contatos.response.ContatoResponse;
import geo.track.gestao.entity.Cliente;
import geo.track.gestao.entity.Contato;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.dto.clientes.request.RequestPutCliente;
import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.dto.clientes.response.ClienteVeiculoResponse;
import geo.track.gestao.service.cliente.*;
import geo.track.gestao.util.ClientesMapper;
import geo.track.gestao.util.ContatoMapper;
import geo.track.gestao.service.ClienteService;
import geo.track.gestao.service.ContatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController implements ClienteSwagger {
    private final ClienteService CLIENTE_SERVICE;
    private final ContatoService CONTATO_SERVICE;
    private final CadastrarClienteUseCase CADASTRAR_CLIENTE_USECASE;
    private final AtualizarClienteUseCase ATUALIZAR_CLIENTE_USECASE;
    private final DeletarClienteUseCase DELETAR_CLIENTE_USECASE;

    @Override
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> findAllClientes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpfCnpj
    ) {
        List<Cliente> clientes = CLIENTE_SERVICE.listarClientes(nome, cpfCnpj);

        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<ClienteResponse> response = ClientesMapper.toResponse(clientes);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/clientes-paginados")
    public ResponseEntity<Page<ClienteResponse>> listarTodos(
            @PageableDefault(size = 8, sort = "idCliente") Pageable pageable) {

        Page<Cliente> clientes = CLIENTE_SERVICE.listarClientesPaginados(pageable);

        Page<ClienteResponse> response = clientes.map(ClientesMapper::toResponse);

        if (response.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

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
        Cliente cliente = CLIENTE_SERVICE.buscarClientePorId(id);

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

    @GetMapping("/{idCliente}/contatos")
    public ResponseEntity<List<ContatoResponse>> listarContatosPorCliente(@PathVariable Integer idCliente) {
        List<Contato> contatos = CONTATO_SERVICE.listarContatosPorCliente(idCliente);

        if (contatos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ContatoMapper.toResponse(contatos));
    }
}