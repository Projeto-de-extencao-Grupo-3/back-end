package geo.track.gestao.service.veiculo;

import geo.track.gestao.entity.Veiculo;
import geo.track.dto.veiculos.request.RequestPatchCor;

public interface AlterarCorVeiculoUseCase {
    Veiculo execute(RequestPatchCor request);
}

