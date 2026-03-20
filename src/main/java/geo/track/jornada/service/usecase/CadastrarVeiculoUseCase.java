package geo.track.jornada.service.usecase;

import geo.track.domain.Veiculo;
import geo.track.dto.veiculos.request.RequestPostVeiculo;

public interface CadastrarVeiculoUseCase {
    Veiculo execute(RequestPostVeiculo status);
}
