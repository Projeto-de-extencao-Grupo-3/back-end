package geo.track.gestao.veiculo.domain;

import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
import geo.track.gestao.veiculo.application.AtualizarVeiculoUseCase;
import geo.track.gestao.veiculo.infraestructure.VeiculoMapper;
import geo.track.gestao.veiculo.infraestructure.request.RequestPutVeiculo;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtualizarVeiculo implements AtualizarVeiculoUseCase {
    private final VeiculoRepository VEICULO_REPOSITORY;
    private final VeiculoService VEICULO_SERVICE;
    private final Log log;

    public Veiculo execute(Integer id, RequestPutVeiculo body) {
        log.info("Atualizando dados completos do veículo ID: {}", id);
        Veiculo veiculo = VEICULO_SERVICE.buscarVeiculoPeloId(id);

        veiculo = VeiculoMapper.toEntityUpdate(veiculo, body);
        veiculo = VEICULO_REPOSITORY.save(veiculo);
        log.info("Veículo ID {} atualizado com sucesso", id);
        return veiculo;
    }
}

