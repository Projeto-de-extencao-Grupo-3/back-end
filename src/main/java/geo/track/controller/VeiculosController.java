package geo.track.controller;

import geo.track.domain.Veiculos;
import geo.track.dto.veiculos.request.RequestPatchCor;
import geo.track.dto.veiculos.request.RequestPatchPlaca;
import geo.track.service.VeiculosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculosController {

    private final VeiculosService service;

    @PostMapping
    public ResponseEntity<Veiculos>cadastrar(@Valid @RequestBody Veiculos veiculo){
        Veiculos veicCadastrado = service.cadastrar(veiculo);
        return ResponseEntity.status(201).body(veiculo);
    }

    @GetMapping
    public ResponseEntity<List<Veiculos>>listar(){
        List<Veiculos>listaVeiculos = service.listar();

        if(listaVeiculos.isEmpty()){
            return ResponseEntity.status(204).body(listaVeiculos);
        }

        return ResponseEntity.status(200).body(listaVeiculos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculos> findVeiculoById(@PathVariable Integer id){
        Veiculos veic = service.findVeiculoById(id);
        return ResponseEntity.status(200).body(veic);
   }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<List<Veiculos>>findVeiculoByPlaca(@PathVariable String placa){
        List<Veiculos>veic = service.findVeiculoByPlaca(placa);

        if(veic.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(veic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculos>putVeiculo(@PathVariable Integer id, @RequestBody Veiculos veiculoAtt){
        Veiculos veic = service.putEndereco(id, veiculoAtt);
        return ResponseEntity.status(200).body(veic);
    }

    @PatchMapping("/placa")
    public ResponseEntity<Veiculos>patchPlaca(@RequestBody RequestPatchPlaca veiculoDTO){
        Veiculos veic = service.patchPlaca(veiculoDTO);
        return ResponseEntity.status(200).body(veic);
    }

    //---
    @PatchMapping("/cor")
    public ResponseEntity<Veiculos>patchCor(@RequestBody RequestPatchCor veiculoDTO){
        Veiculos veic = service.patchCor(veiculoDTO);
        return ResponseEntity.status(200).body(veic);
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteVeiculoById(@PathVariable Integer id){
        service.deleteVeiculoById(id);
        return ResponseEntity.status(204).build();
   }

    @DeleteMapping("/placa/{placa}")
    public ResponseEntity<Void>deleteVeiculoByPlaca(@PathVariable String placa){
        service.deleteVeiculoByPlaca(placa);
        return ResponseEntity.status(204).build();
    }

}
