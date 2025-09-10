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
        return service.cadastrar(veiculo);
    }

    @GetMapping
    public ResponseEntity<List<Veiculos>>listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculos>findVeiculoById(@PathVariable Integer id){
        return service.findVeiculoById(id);
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<List<Veiculos>>findVeiculoByPlaca(@PathVariable String placa){
        return service.findVeiculoByPlaca(placa);
    }

    @PutMapping()
    public ResponseEntity<Veiculos>putVeiculo(@RequestBody RequestPutVeiculos veiculoDTO){
        return service.putEndereco(veiculoDTO);
    }

    @PatchMapping("/placa")
    public ResponseEntity<Veiculos>patchPlaca(@RequestBody RequestPatchPlaca veiculoDTO){
        return service.patchPlaca(veiculoDTO);
    }

    @PatchMapping("/cor")
    public ResponseEntity<Veiculos>patchPlaca(@RequestBody RequestPatchCor veiculoDTO){
        return service.patchCor(veiculoDTO);
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteVeiculoById(@PathVariable Integer id){
        return service.deleteVeiculoById(id);
   }

    @DeleteMapping("/placa/{placa}")
    public ResponseEntity<Void>deleteVeiculoByPlaca(@PathVariable String placa){
        return service.deleteVeiculoByPlaca(placa);
    }

}
