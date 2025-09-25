package geo.track.controller;

import geo.track.domain.Veiculos;
import geo.track.request.veiculos.RequestPatchCor;
import geo.track.request.veiculos.RequestPatchPlaca;
import geo.track.request.veiculos.RequestPutVeiculos;
import geo.track.service.VeiculosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculosController {

    public final VeiculosService service;

    public VeiculosController(VeiculosService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Veiculos>cadastrar(@RequestBody Veiculos veiculo){
        Veiculos veicCadastrado = service.cadastrar(veiculo);
        return ResponseEntity.status(201).body(veiculo);
    }

    @GetMapping
    public ResponseEntity<List<Veiculos>>listar(){
        List<Veiculos>listaVeiculos = service.listar();
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

    @PutMapping()
    public ResponseEntity<Veiculos>putVeiculo(@RequestBody RequestPutVeiculos veiculoDTO){
        Veiculos veic = service.putEndereco(veiculoDTO);
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
        return service.deleteVeiculoById(id);
   }

    @DeleteMapping("/placa/{placa}")
    public ResponseEntity<Void>deleteVeiculoByPlaca(@PathVariable String placa){
        return ResponseEntity.status(204).build();
    }

}
