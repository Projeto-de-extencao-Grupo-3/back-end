package geo.track.gestao.service.veiculo.implementations;

import geo.track.gestao.entity.Veiculo;
import geo.track.gestao.entity.repository.VeiculoRepository;
import geo.track.gestao.service.VeiculoService;
import geo.track.gestao.service.veiculo.AtualizarVeiculoUseCase;
import geo.track.gestao.util.VeiculoMapper;
import geo.track.dto.veiculos.request.RequestPutVeiculo;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.VeiculoExceptionMessages;
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

