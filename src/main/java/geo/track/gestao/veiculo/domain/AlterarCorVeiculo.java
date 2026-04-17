package geo.track.gestao.veiculo.domain;

import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
import geo.track.gestao.veiculo.infraestructure.request.RequestPatchCor;
import geo.track.gestao.veiculo.application.AlterarCorVeiculoUseCase;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

