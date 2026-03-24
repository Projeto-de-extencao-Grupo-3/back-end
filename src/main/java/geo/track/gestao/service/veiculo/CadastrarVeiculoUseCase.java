package geo.track.gestao.service.veiculo;

import geo.track.gestao.entity.Veiculo;
import geo.track.dto.veiculos.request.RequestPostVeiculo;

public interface CadastrarVeiculoUseCase {
    Veiculo execute(RequestPostVeiculo request);
}
