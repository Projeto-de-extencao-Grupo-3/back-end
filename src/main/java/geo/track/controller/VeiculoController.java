package geo.track.controller;

import geo.track.controller.swagger.VeiculoSwagger;
import geo.track.domain.Veiculo;
import geo.track.dto.veiculos.request.RequestPatchCor;
import geo.track.dto.veiculos.request.RequestPatchPlaca;
import geo.track.dto.veiculos.response.VeiculoResponse;
import geo.track.mapper.VeiculoMapper;
import geo.track.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController implements VeiculoSwagger {

    private final VeiculoService service;

    @Override
    @PostMapping
    public ResponseEntity<VeiculoResponse>cadastrar(@Valid @RequestBody Veiculo veiculo){
        Veiculo veicCadastrado = service.cadastrar(veiculo);
        return ResponseEntity.status(201).body(VeiculoMapper.toResponse(veicCadastrado));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<VeiculoResponse>>listar(){
        List<Veiculo>listaVeiculos = service.listar();

        if(listaVeiculos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(listaVeiculos));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> findVeiculoById(@PathVariable Integer id){
        Veiculo veic = service.findVeiculoById(id);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Override
    @GetMapping("/placa/{placa}")
    public ResponseEntity<List<VeiculoResponse>>findVeiculoByPlaca(@PathVariable String placa){
        List<Veiculo>veic = service.findVeiculoByPlaca(placa);

        if(veic.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponse>putVeiculo(@PathVariable Integer id, @RequestBody Veiculo veiculoAtt){
        Veiculo veic = service.putEndereco(id, veiculoAtt);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Override
    @PatchMapping("/placa")
    public ResponseEntity<VeiculoResponse>patchPlaca(@RequestBody RequestPatchPlaca veiculoDTO){
        Veiculo veic = service.patchPlaca(veiculoDTO);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Override
    @PatchMapping("/cor")
    public ResponseEntity<VeiculoResponse>patchCor(@RequestBody RequestPatchCor veiculoDTO){
        Veiculo veic = service.patchCor(veiculoDTO);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    // ...existing code...
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