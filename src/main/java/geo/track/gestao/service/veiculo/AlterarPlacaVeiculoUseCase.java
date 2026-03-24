package geo.track.gestao.service.veiculo;

import geo.track.gestao.entity.Veiculo;
import geo.track.dto.veiculos.request.RequestPatchPlaca;

public interface AlterarPlacaVeiculoUseCase {
    Veiculo execute(RequestPatchPlaca request);
}

