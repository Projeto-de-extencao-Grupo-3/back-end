package geo.track.gestao.veiculo.application;

import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.request.RequestPutVeiculo;

public interface AtualizarVeiculoUseCase {
    Veiculo execute(Integer id, RequestPutVeiculo request);
}

