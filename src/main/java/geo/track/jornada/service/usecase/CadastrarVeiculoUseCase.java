package geo.track.jornada.service.usecase;

import geo.track.domain.Veiculo;
import geo.track.dto.veiculos.request.RequestPostVeiculo;
import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.request.entrada.RequestEntradaEfetivaSemCadastro;

public interface CadastrarVeiculoUseCase {
    Veiculo execute(RequestPostVeiculo status);
}
