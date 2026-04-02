package geo.track.gestao;

import geo.track.controller.swagger.EnderecoSwagger;
import geo.track.gestao.entity.Endereco;
import geo.track.dto.enderecos.request.RequestPatchComplemento;
import geo.track.dto.enderecos.request.RequestPatchNumero;
import geo.track.dto.enderecos.request.RequestPostEndereco;
import geo.track.dto.enderecos.request.RequestPutEndereco;
import geo.track.dto.enderecos.response.EnderecoResponse;
import geo.track.dto.viacep.response.ResponseViacep;
import geo.track.gestao.service.endereco.*;
import geo.track.gestao.util.EnderecoMapper;
import geo.track.gestao.service.EnderecoService;
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
        Endereco endereco = ENDERECO_SERVICE.findEnderecoById(id);

        if (endereco == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }

    @Override
    @GetMapping("/viacep/{cep}")
    public ResponseEntity<ResponseViacep> findEnderecoByVIACEP(@PathVariable String cep) {
        ResponseViacep responseCep = ENDERECO_SERVICE.findEnderecoByVIACEP(cep);

        if (responseCep == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(responseCep);
    }

    @Override
    @PostMapping()
    public ResponseEntity<EnderecoResponse> postEndereco(@RequestBody @Valid RequestPostEndereco body) {
        Endereco novoEndereco = CADASTRAR_ENDERECO_USECASE.execute(body);
        return ResponseEntity.status(201).body(EnderecoMapper.toResponse(novoEndereco));
    }

    @Override
    @PatchMapping("/complemento")
    public ResponseEntity<EnderecoResponse> patchComplementoEndereco(@RequestBody @Valid RequestPatchComplemento body) {
        Endereco endereco = ALTERAR_COMPLEMENTO_ENDERECO_USECASE.execute(body);
        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }

    @Override
    @PatchMapping("/numero")
    public ResponseEntity<EnderecoResponse> patchNumeroEndereco(@RequestBody @Valid RequestPatchNumero body) {
        Endereco endereco = ALTERAR_NUMERO_ENDERECO_USECASE.execute(body);
        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }

    @Override
    @PutMapping()
    public ResponseEntity<EnderecoResponse> putEndereco(@RequestBody @Valid RequestPutEndereco body) {
        Endereco endereco = ATUALIZAR_ENDERECO_USECASE.execute(body);
        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }
}