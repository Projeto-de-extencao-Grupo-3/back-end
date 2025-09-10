package geo.track.service;

import geo.track.domain.Enderecos;
import geo.track.domain.Veiculos;
import geo.track.repository.VeiculosRepository;
import geo.track.request.veiculos.RequestPatchCor;
import geo.track.request.veiculos.RequestPatchPlaca;
import geo.track.request.veiculos.RequestPutVeiculos;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class VeiculosService {
    public final VeiculosRepository repository;

    public VeiculosService(VeiculosRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Veiculos>cadastrar(@RequestBody Veiculos veiculo){
        veiculo.setIdVeiculo(null);

        if(repository.existsByPlacaIgnoreCase(veiculo.getPlaca())){
            return ResponseEntity.status(409).build();
        }

        return ResponseEntity.status(201).body(repository.save(veiculo));
    }

    public ResponseEntity<List<Veiculos>>listar(){
        List<Veiculos>veiculosEncontrados = repository.findAll();

        if(veiculosEncontrados.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(veiculosEncontrados);
    }

    public ResponseEntity<Veiculos> findVeiculoById(@PathVariable Integer id){
        Optional<Veiculos> veiculo = repository.findById(id);

        if(veiculo.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(veiculo.get());
    }

    public ResponseEntity<List<Veiculos>> findVeiculoByPlaca(@PathVariable String placa){
        List<Veiculos>veiculosEncontrados = repository.findAllByPlacaStartsWithIgnoreCase(placa);

        if(veiculosEncontrados.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(veiculosEncontrados);
    }

    public ResponseEntity<Veiculos> putEndereco(RequestPutVeiculos veiculoDTO){
        Optional<Veiculos> veiculoOpt = repository.findById(veiculoDTO.getIdVeiculo());

        if(veiculoOpt.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        Veiculos veiculo = veiculoOpt.get();

        veiculo.setIdVeiculo(veiculoDTO.getIdVeiculo());
        veiculo.setPlaca(veiculoDTO.getPlaca());
        veiculo.setMarca(veiculoDTO.getMarca());
        veiculo.setAnoModelo(veiculoDTO.getAnoModelo());
        veiculo.setAnoFabricacao(veiculoDTO.getAnoFabricacao());
        veiculo.setCor(veiculoDTO.getCor());

        repository.save(veiculo);

        return ResponseEntity.status(200).body(veiculo);
    }

    public ResponseEntity<Veiculos> patchPlaca(RequestPatchPlaca veiculoDTO){
        Optional<Veiculos> veiculoOpt = repository.findById(veiculoDTO.getIdVeiculo());

        if(veiculoOpt.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        Veiculos veiculo = veiculoOpt.get();

        veiculo.setIdVeiculo(veiculoDTO.getIdVeiculo());
        veiculo.setPlaca(veiculoDTO.getPlaca());

        repository.save(veiculo);

        return ResponseEntity.status(200).body(veiculo);
    }

    public ResponseEntity<Veiculos> patchCor(RequestPatchCor veiculoDTO){
        Optional<Veiculos> veiculoOpt = repository.findById(veiculoDTO.getIdVeiculo());

        if(veiculoOpt.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        Veiculos veiculo = veiculoOpt.get();

        veiculo.setIdVeiculo(veiculoDTO.getIdVeiculo());
        veiculo.setCor(veiculoDTO.getCor());

        repository.save(veiculo);

        return ResponseEntity.status(200).body(veiculo);
    }

     public ResponseEntity<Void>deleteVeiculoById(@PathVariable Integer id){
         if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
         }

         return ResponseEntity.status(404).build();

     }

    public ResponseEntity<Void>deleteVeiculoByPlaca(@PathVariable String placa){
        if(repository.existsByPlacaIgnoreCase(placa)){
            repository.deleteByPlacaIgnoreCase(placa);
            return ResponseEntity.status(204).build();
        }
            return ResponseEntity.status(404).build();

    }
}
