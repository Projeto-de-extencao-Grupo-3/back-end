package geo.track.gestao.cliente.infraestructure.web;

import geo.track.gestao.cliente.application.endereco.*;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPatchComplemento;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPatchNumero;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPostEndereco;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPutEndereco;
import geo.track.gestao.cliente.infraestructure.response.endereco.EnderecoResponse;
import geo.track.externo.viacep.response.ResponseViacep;
import geo.track.gestao.cliente.infraestructure.EnderecoMapper;
import geo.track.gestao.cliente.domain.endereco.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes/{idCliente}/enderecos")
@RequiredArgsConstructor
public class EnderecoController {
    private final EnderecoService ENDERECO_SERVICE;
    private final CadastrarEnderecoUseCase CADASTRAR_ENDERECO_USECASE;
    private final CriarEnderecoVazioUseCase CRIAR_ENDERECO_VAZIO_USECASE;
    private final AtualizarEnderecoUseCase ATUALIZAR_ENDERECO_USECASE;
    private final DeletarEnderecoUseCase DELETAR_ENDERECO_USECASE;

    @PostMapping("/registrar-vazio")
    public ResponseEntity<EnderecoResponse> registrarEnderecoVazio(@PathVariable Integer idCliente) {
        Endereco enderecoVazio = CRIAR_ENDERECO_VAZIO_USECASE.execute(idCliente);
        return ResponseEntity.status(201).body(EnderecoMapper.toResponse(enderecoVazio));
    }

    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoResponse> getEnderecoById(@PathVariable Integer idCliente, @PathVariable Integer idEndereco) {
        Endereco endereco = ENDERECO_SERVICE.buscarEnderecoPorId(idCliente, idEndereco);

        if (endereco == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }

    @GetMapping("/viacep/{cep}")
    public ResponseEntity<ResponseViacep> findEnderecoByVIACEP(@PathVariable String cep) {
        ResponseViacep responseCep = ENDERECO_SERVICE.buscarEnderecoPorCep(cep);

        if (responseCep == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(responseCep);
    }

    @PostMapping()
    public ResponseEntity<EnderecoResponse> postEndereco(@RequestBody @Valid RequestPostEndereco body, @PathVariable Integer idCliente) {
        Endereco novoEndereco = CADASTRAR_ENDERECO_USECASE.execute(body, idCliente);
        return ResponseEntity.status(201).body(EnderecoMapper.toResponse(novoEndereco));
    }

    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Integer idCliente, @PathVariable Integer idEndereco) {
        DELETAR_ENDERECO_USECASE.execute(idCliente, idEndereco);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoResponse> putEndereco(@PathVariable Integer idCliente, @PathVariable Integer idEndereco, @RequestBody @Valid RequestPutEndereco body) {
        Endereco endereco = ATUALIZAR_ENDERECO_USECASE.execute(idCliente, idEndereco, body);
        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }
}