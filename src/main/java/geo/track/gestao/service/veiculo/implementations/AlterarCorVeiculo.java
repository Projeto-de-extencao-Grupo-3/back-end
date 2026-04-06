package geo.track.gestao.service.veiculo.implementations;

import geo.track.gestao.entity.Veiculo;
import geo.track.gestao.entity.repository.VeiculoRepository;
import geo.track.dto.veiculos.request.RequestPatchCor;
import geo.track.gestao.service.VeiculoService;
import geo.track.gestao.service.veiculo.AlterarCorVeiculoUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.VeiculoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlterarCorVeiculo implements AlterarCorVeiculoUseCase {
    private final VeiculoRepository VEICULO_REPOSITORY;
    private final VeiculoService VEICULO_SERVICE;
    private final Log log;

    public Veiculo execute(RequestPatchCor body) {
        log.info("Solicitação de alteração de cor para o veículo ID: {}", body.getIdVeiculo());
        Veiculo veiculo = VEICULO_SERVICE.buscarVeiculoPeloId(body.getIdVeiculo());

        Veiculo atualizado = VEICULO_REPOSITORY.save(veiculo);
        log.info("Cor do veículo ID {} atualizada com sucesso", atualizado.getIdVeiculo());
        return atualizado;
    }
}

