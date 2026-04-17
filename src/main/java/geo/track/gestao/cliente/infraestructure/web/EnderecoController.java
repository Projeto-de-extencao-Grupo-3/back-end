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

@RequestMapping(path = "/enderecos")
@RestController
@RequiredArgsConstructor
public class EnderecoController implements EnderecoSwagger {
    private final EnderecoService ENDERECO_SERVICE;
    private final CadastrarEnderecoUseCase CADASTRAR_ENDERECO_USECASE;
    private final CriarEnderecoVazioUseCase CRIAR_ENDERECO_VAZIO_USECASE;
    private final AlterarComplementoEnderecoUseCase ALTERAR_COMPLEMENTO_ENDERECO_USECASE;
    private final AlterarNumeroEnderecoUseCase ALTERAR_NUMERO_ENDERECO_USECASE;
    private final AtualizarEnderecoUseCase ATUALIZAR_ENDERECO_USECASE;

    @PostMapping("/registrar-vazio")
    public ResponseEntity<EnderecoResponse> registrarEnderecoVazio() {
        Endereco enderecoVazio = CRIAR_ENDERECO_VAZIO_USECASE.execute();
        return ResponseEntity.status(201).body(EnderecoMapper.toResponse(enderecoVazio));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponse> getEnderecoById(@PathVariable Integer id) {
        Endereco endereco = ENDERECO_SERVICE.buscarEnderecoPorId(id);

        if (endereco == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }

    @Override
    @GetMapping("/viacep/{cep}")
    public ResponseEntity<ResponseViacep> findEnderecoByVIACEP(@PathVariable String cep) {
        ResponseViacep responseCep = ENDERECO_SERVICE.buscarEnderecoPorCep(cep);

        if (responseCep == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(responseCep);
    }

    @Override
    @PostMapping("/{fkCliente}")
    public ResponseEntity<EnderecoResponse> postEndereco(@RequestBody @Valid RequestPostEndereco body, @PathVariable Integer fkCliente) {
        Endereco novoEndereco = CADASTRAR_ENDERECO_USECASE.execute(body, fkCliente);
        return ResponseEntity.status(201).body(EnderecoMapper.toResponse(novoEndereco));
    }

    @Override
    @PatchMapping("/complemento")
    public ResponseEntity<EnderecoResponse> patchComplementoEndereco(@RequestBody @Valid RequestPatchComplemento body) {
        Endereco endereco = ALTERAR_COMPLEMENTO_ENDERECO_USECASE.execute(body.getIdEndereco(), body.getComplemento());
        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }

    @Override
    @PatchMapping("/numero")
    public ResponseEntity<EnderecoResponse> patchNumeroEndereco(@RequestBody @Valid RequestPatchNumero body) {
        Endereco endereco = ALTERAR_NUMERO_ENDERECO_USECASE.execute(body.getId(), body.getNumero());
        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }

    @Override
    @PutMapping()
    public ResponseEntity<EnderecoResponse> putEndereco(@RequestBody @Valid RequestPutEndereco body) {
        Endereco endereco = ATUALIZAR_ENDERECO_USECASE.execute(body);
        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }
}