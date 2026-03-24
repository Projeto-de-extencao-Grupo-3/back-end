package geo.track.gestao.service.veiculo;

import geo.track.gestao.entity.Veiculo;
import geo.track.dto.veiculos.request.RequestPutVeiculo;

public interface AtualizarVeiculoUseCase {
    Veiculo execute(Integer id, RequestPutVeiculo request);
}

