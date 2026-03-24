package geo.track.gestao.service.veiculo.implementations;

import geo.track.gestao.entity.Veiculo;
import geo.track.gestao.entity.repository.VeiculoRepository;
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
public class AtualizarVeiculoImplementation implements AtualizarVeiculoUseCase {
    private final VeiculoRepository VEICULO_REPOSITORY;
    private final Log log;

    public Veiculo execute(Integer id, RequestPutVeiculo body) {
        log.info("Atualizando dados completos do veículo ID: {}", id);
        Veiculo veic = VEICULO_REPOSITORY.findById(id).orElseThrow(() -> {
            log.error("Falha na atualização: Veículo ID {} não encontrado", id);
            return new DataNotFoundException(VeiculoExceptionMessages.VEICULO_NAO_ENCONTRADO_ID, Domains.VEICULO);
        });

        veic = VeiculoMapper.toEntityUpdate(veic, body);
        veic = VEICULO_REPOSITORY.save(veic);
        log.info("Veículo ID {} atualizado com sucesso", id);
        return veic;
    }
}

