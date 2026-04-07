package geo.track.jornada.service.controle;

import geo.track.jornada.entity.OrdemDeServico;

public interface DefinirPagamentoRealizadoUseCase {
    OrdemDeServico execute(Integer idOrdemServico);
}
