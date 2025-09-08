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
        return enderecosService.findEnderecoById(id);
    }

    @GetMapping("/viacep/{cep}")
    public ResponseEntity<ResponseViacep> findEnderecoByVIACEP(@PathVariable String cep) {
        return enderecosService.findEnderecoByVIACEP(cep);
    }

    @PostMapping()
    public ResponseEntity<Enderecos> postEndereco(@RequestBody Enderecos endereco) {
        return enderecosService.postEndereco(endereco);
    }

    @PatchMapping("/complemento")
    public ResponseEntity<Enderecos> patchComplementoEndereco(@RequestBody RequestPatchComplemento enderecoDTO) {
        return enderecosService.patchComplementoEndereco(enderecoDTO);
    }

    @PatchMapping("/numero")
    public ResponseEntity<Enderecos> patchNumeroEndereco(@RequestBody RequestPatchNumero enderecoDTO) {
        return enderecosService.patchNumeroEndereco(enderecoDTO);
    }

    @PutMapping()
    public ResponseEntity<Enderecos> putEndereco(@RequestBody RequestPutEndereco enderecoDTO) {
        return enderecosService.putEndereco(enderecoDTO);
    }

}
