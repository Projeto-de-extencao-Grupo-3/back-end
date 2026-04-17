package geo.track.gestao.veiculo.application;

import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.request.RequestPatchPlaca;

public interface AlterarPlacaVeiculoUseCase {
    Veiculo execute(RequestPatchPlaca request);
}

