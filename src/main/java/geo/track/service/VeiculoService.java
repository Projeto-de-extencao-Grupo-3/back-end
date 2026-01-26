package geo.track.service;

import geo.track.domain.Veiculo;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.VeiculoRepository;
import geo.track.dto.veiculos.request.RequestPatchCor;
import geo.track.dto.veiculos.request.RequestPatchPlaca;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class VeiculoService {
    private final VeiculoRepository repository;

    public Veiculo cadastrar(@RequestBody Veiculo veiculo){
        veiculo.setIdVeiculo(null);

        if(repository.existsByPlacaIgnoreCase(veiculo.getPlaca())){
            throw new ConflictException("A placa do veículo informada já existe", "Veiculo");
        }

        return repository.save(veiculo);
    }

    public List<Veiculo>listar(){
        return repository.findAll();
    }

    public Veiculo findVeiculoById(@PathVariable Integer id){
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Não existe um veículo com esse ID", "Veiculo")
        );
    }

    public List<Veiculo> findVeiculoByPlaca(@PathVariable String placa){
        return repository.findAllByPlacaStartsWithIgnoreCase(placa);
    }

    public Veiculo putEndereco(Integer id, Veiculo veiculoAtt){
        if(repository.existsById(id)){
            veiculoAtt.setIdVeiculo(id);
            Veiculo veic = repository.save(veiculoAtt);
            return veic;
        }

        throw new DataNotFoundException("Não existe um veículo com esse ID", "Veiculo");
    }

    public Veiculo patchPlaca(RequestPatchPlaca veiculoDTO){
        Optional<Veiculo> veiculoOpt = repository.findById(veiculoDTO.getIdVeiculo());

        if(veiculoOpt.isEmpty()){
            throw new DataNotFoundException("Não existe um veículo com esse ID", "Veiculo");
        }

        Veiculo veiculo = veiculoOpt.get();

        veiculo.setIdVeiculo(veiculoDTO.getIdVeiculo());
        veiculo.setPlaca(veiculoDTO.getPlaca());

        return repository.save(veiculo);
    }

    public Veiculo patchCor(RequestPatchCor veiculoDTO){
        Optional<Veiculo> veiculoOpt = repository.findById(veiculoDTO.getIdVeiculo());

        if(veiculoOpt.isEmpty()){
            throw new DataNotFoundException("Não existe um veículo com esse ID", "Veiculo");
        }

        Veiculo veiculo = veiculoOpt.get();

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
