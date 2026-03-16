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
    private final VeiculoService VEICULO_SERVICE;

    @Override
    @PostMapping
    public ResponseEntity<VeiculoResponse>cadastrar(@Valid @RequestBody Veiculo body){
        Veiculo veicCadastrado = VEICULO_SERVICE.cadastrar(body);
        return ResponseEntity.status(201).body(VeiculoMapper.toResponse(veicCadastrado));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<VeiculoResponse>>listar(){
        List<Veiculo>listaVeiculos = VEICULO_SERVICE.listar();

        if(listaVeiculos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(listaVeiculos));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> findVeiculoById(@PathVariable Integer id){
        Veiculo veic = VEICULO_SERVICE.findVeiculoById(id);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Override
    @GetMapping("/placa/{placa}")
    public ResponseEntity<List<VeiculoResponse>>findVeiculoByPlaca(@PathVariable String placa){
        List<Veiculo>veic = VEICULO_SERVICE.findVeiculoByPlaca(placa);

        if(veic.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @GetMapping("/cliente/{id}")
    @Override
    public ResponseEntity<List<VeiculoResponse>>findVeiculoByClienteId(@PathVariable Integer id){
        List<Veiculo> veiculos = VEICULO_SERVICE.findVeiculoByCliente(id);

        if(veiculos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veiculos));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponse>putVeiculo(@PathVariable Integer id, @RequestBody Veiculo body){
        Veiculo veic = VEICULO_SERVICE.putEndereco(id, body);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Override
    @PatchMapping("/placa")
    public ResponseEntity<VeiculoResponse>patchPlaca(@RequestBody @Valid RequestPatchPlaca body){
        Veiculo veic = VEICULO_SERVICE.patchPlaca(body);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Override
    @PatchMapping("/cor")
    public ResponseEntity<VeiculoResponse>patchCor(@RequestBody @Valid RequestPatchCor veiculoDTO){
        Veiculo veic = VEICULO_SERVICE.patchCor(veiculoDTO);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteVeiculoById(@PathVariable Integer id){
        VEICULO_SERVICE.deleteVeiculoById(id);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/placa/{placa}")
    public ResponseEntity<Void>deleteVeiculoByPlaca(@PathVariable String placa){
        VEICULO_SERVICE.deleteVeiculoByPlaca(placa);
        return ResponseEntity.status(204).build();
    }
}