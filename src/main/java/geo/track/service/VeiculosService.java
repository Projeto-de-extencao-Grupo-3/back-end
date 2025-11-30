package geo.track.service;

import geo.track.domain.Produtos;
import geo.track.domain.Veiculos;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.VeiculosRepository;
import geo.track.dto.veiculos.request.RequestPatchCor;
import geo.track.dto.veiculos.request.RequestPatchPlaca;
import geo.track.dto.veiculos.request.RequestPutVeiculos;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class VeiculosService {
    private final VeiculosRepository repository;

    public Veiculos cadastrar(@RequestBody Veiculos veiculo){
        veiculo.setIdVeiculo(null);

        if(repository.existsByPlacaIgnoreCase(veiculo.getPlaca())){
            throw new ConflictException("A placa do veículo informada já existe", "Veiculo");
        }

        return repository.save(veiculo);
    }

    public List<Veiculos>listar(){
        return repository.findAll();
    }

    public Veiculos findVeiculoById(@PathVariable Integer id){
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Não existe um veículo com esse ID", "Veiculo")
        );
    }

    public List<Veiculos> findVeiculoByPlaca(@PathVariable String placa){
        return repository.findAllByPlacaStartsWithIgnoreCase(placa);
    }

    public Veiculos putEndereco(Integer id,Veiculos veiculoAtt){
        if(repository.existsById(id)){
            veiculoAtt.setIdVeiculo(id);
            Veiculos veic = repository.save(veiculoAtt);
            return veic;
        }

        throw new DataNotFoundException("Não existe um veículo com esse ID", "Veiculo");
    }

    public Veiculos patchPlaca(RequestPatchPlaca veiculoDTO){
        Optional<Veiculos> veiculoOpt = repository.findById(veiculoDTO.getIdVeiculo());

        if(veiculoOpt.isEmpty()){
            throw new DataNotFoundException("Não existe um veículo com esse ID", "Veiculo");
        }

        Veiculos veiculo = veiculoOpt.get();

        veiculo.setIdVeiculo(veiculoDTO.getIdVeiculo());
        veiculo.setPlaca(veiculoDTO.getPlaca());

        return repository.save(veiculo);
    }

    public Veiculos patchCor(RequestPatchCor veiculoDTO){
        Optional<Veiculos> veiculoOpt = repository.findById(veiculoDTO.getIdVeiculo());

        if(veiculoOpt.isEmpty()){
            throw new DataNotFoundException("Não existe um veículo com esse ID", "Veiculo");
        }

        Veiculos veiculo = veiculoOpt.get();

        veiculo.setIdVeiculo(veiculoDTO.getIdVeiculo());

        return repository.save(veiculo);
    }

     public void deleteVeiculoById(@PathVariable Integer id){
         if(!repository.existsById(id)){
             throw new DataNotFoundException("Não existe um veículo com esse ID!", "Veiculo");
         }

         repository.deleteById(id);

     }

    public void deleteVeiculoByPlaca(@PathVariable String placa){
        if(!repository.existsByPlacaIgnoreCase(placa)){
            throw new DataNotFoundException("Não existe um veículo com essa Placa!", "Veiculo");
        }

        repository.deleteByPlacaIgnoreCase(placa);
    }
}
