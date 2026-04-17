package geo.track.gestao.veiculo.application;

import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.request.RequestPostVeiculo;

public interface CadastrarVeiculoUseCase {
    Veiculo execute(RequestPostVeiculo request);
}
