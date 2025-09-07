package geo.track.controller;

import geo.track.domain.Enderecos;
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

    @GetMapping("/cep/{cep}")
    public ResponseEntity<Enderecos> findEnderecoByVIACEP(@PathVariable String cep) {
        return enderecosService.findEnderecoByVIACEP(cep);
    }

    @PostMapping()
    public ResponseEntity<Enderecos> postEndereco(@RequestBody Enderecos endereco) {
        return enderecosService.postEndereco(endereco);
    }
}
