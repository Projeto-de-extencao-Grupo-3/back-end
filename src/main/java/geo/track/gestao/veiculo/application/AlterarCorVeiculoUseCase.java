package geo.track.gestao.veiculo.application;

import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.request.RequestPatchCor;

public interface AlterarCorVeiculoUseCase {
    Veiculo execute(RequestPatchCor request);
}

