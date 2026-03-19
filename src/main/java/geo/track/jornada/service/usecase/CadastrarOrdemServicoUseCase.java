package geo.track.jornada.service.usecase;

import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServico;

public interface CadastrarOrdemServicoUseCase {
    OrdemDeServico execute(StatusVeiculo status);
}
