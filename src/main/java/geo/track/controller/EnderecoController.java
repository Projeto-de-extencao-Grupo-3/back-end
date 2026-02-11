package geo.track.controller;

import geo.track.controller.swagger.EnderecoSwagger;
import geo.track.domain.Endereco;
import geo.track.dto.enderecos.request.RequestPatchComplemento;
import geo.track.dto.enderecos.request.RequestPatchNumero;
import geo.track.dto.enderecos.request.RequestPostEndereco;
import geo.track.dto.enderecos.request.RequestPutEndereco;
import geo.track.dto.enderecos.response.EnderecoResponse;
import geo.track.dto.viacep.response.ResponseViacep;
import geo.track.mapper.EnderecoMapper;
import geo.track.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/enderecos")
@RestController
@RequiredArgsConstructor
public class EnderecoController implements EnderecoSwagger {
    private final EnderecoService enderecoService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponse> getEnderecoById(@PathVariable Integer id) {
        Endereco endereco = enderecoService.findEnderecoById(id);

        if (endereco == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }

    @Override
    @GetMapping("/viacep/{cep}")
    public ResponseEntity<ResponseViacep> findEnderecoByVIACEP(@PathVariable String cep) {
        ResponseViacep responseCep = enderecoService.findEnderecoByVIACEP(cep);

        if (responseCep == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(responseCep);
    }

    @Override
    @PostMapping()
    public ResponseEntity<EnderecoResponse> postEndereco(@RequestBody RequestPostEndereco endereco) {
        Endereco novoEndereco = enderecoService.postEndereco(endereco);
        return ResponseEntity.status(201).body(EnderecoMapper.toResponse(novoEndereco));
    }

    @Override
    @PatchMapping("/complemento")
    public ResponseEntity<EnderecoResponse> patchComplementoEndereco(@RequestBody RequestPatchComplemento enderecoDTO) {
        Endereco endereco = enderecoService.patchComplementoEndereco(enderecoDTO);
        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }

    @Override
    @PatchMapping("/numero")
    public ResponseEntity<EnderecoResponse> patchNumeroEndereco(@RequestBody RequestPatchNumero enderecoDTO) {
        Endereco endereco = enderecoService.patchNumeroEndereco(enderecoDTO);
        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }

    @Override
    @PutMapping()
    public ResponseEntity<EnderecoResponse> putEndereco(@RequestBody RequestPutEndereco enderecoDTO) {
        Endereco endereco = enderecoService.putEndereco(enderecoDTO);
        return ResponseEntity.status(200).body(EnderecoMapper.toResponse(endereco));
    }
}