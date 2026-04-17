package geo.track.gestao.veiculo.domain;

import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.VeiculoExceptionMessages;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {
    private final VeiculoRepository VEICULO_REPOSITORY;
    private final Log log;

    public List<Veiculo> listarVeiculos(){
        log.info("Listando todos os veículos");
        return VEICULO_REPOSITORY.findAll();
    }

    public Veiculo buscarVeiculoPeloId(@PathVariable Integer id){
        log.info("Buscando veículo pelo ID: {}", id);
        return VEICULO_REPOSITORY.findById(id).orElseThrow(
                () -> {
                    log.error("Veículo com ID {} não encontrado", id);
                    return new DataNotFoundException(VeiculoExceptionMessages.VEICULO_NAO_ENCONTRADO_ID, Domains.VEICULO);
                }
        );
    }

    public List<Veiculo> buscarVeiculoPelaPlaca(@PathVariable String placa){
        log.info("Buscando veículos que iniciam com a placa: {}", placa);
        return VEICULO_REPOSITORY.findAllByPlacaStartsWithIgnoreCase(placa);
    }


    public List<Veiculo> buscarVeiculoPeloIdCliente(Integer idCliente) {
        return VEICULO_REPOSITORY.findAllByIdCliente(idCliente);
    }
}
