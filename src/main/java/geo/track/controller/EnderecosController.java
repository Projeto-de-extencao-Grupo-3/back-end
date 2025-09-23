package geo.track.controller;

import geo.track.domain.Enderecos;
import geo.track.request.enderecos.RequestPatchComplemento;
import geo.track.request.enderecos.RequestPatchNumero;
import geo.track.request.enderecos.RequestPutEndereco;
import geo.track.request.viacep.ResponseViacep;
import geo.track.service.EnderecosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/enderecos")
@RestController
@RequiredArgsConstructor
public class EnderecosController {
    public final EnderecosService enderecosService;

    @GetMapping("/{id}")
    public ResponseEntity<Enderecos> getEnderecoById(@PathVariable Integer id) {
        Enderecos enderecos = enderecosService.findEnderecoById(id);

        if (enderecos == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(enderecos);
    }

    @GetMapping("/viacep/{cep}")
    public ResponseEntity<ResponseViacep> findEnderecoByVIACEP(@PathVariable String cep) {
        ResponseViacep responseCep = enderecosService.findEnderecoByVIACEP(cep);

        if (responseCep == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(responseCep);
    }

    @PostMapping()
    public ResponseEntity<Enderecos> postEndereco(@RequestBody Enderecos endereco) {
        return ResponseEntity.status(201).body(enderecosService.postEndereco(endereco));
    }

    @PatchMapping("/complemento")
    public ResponseEntity<Enderecos> patchComplementoEndereco(@RequestBody RequestPatchComplemento enderecoDTO) {
        Enderecos enderecos = enderecosService.patchComplementoEndereco(enderecoDTO);

        if (enderecos == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(enderecos);
    }

    @PatchMapping("/numero")
    public ResponseEntity<Enderecos> patchNumeroEndereco(@RequestBody RequestPatchNumero enderecoDTO) {
        Enderecos enderecos = enderecosService.patchNumeroEndereco(enderecoDTO);

        if (enderecos == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(enderecos);
    }

    @PutMapping()
    public ResponseEntity<Enderecos> putEndereco(@RequestBody RequestPutEndereco enderecoDTO) {
        Enderecos enderecos = enderecosService.putEndereco(enderecoDTO);

        if (enderecos == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(enderecos);
    }

}
