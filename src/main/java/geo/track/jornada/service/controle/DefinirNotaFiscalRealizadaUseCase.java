package geo.track.jornada.service.controle;

import geo.track.jornada.entity.OrdemDeServico;

public interface DefinirNotaFiscalRealizadaUseCase {
    OrdemDeServico execute(Integer idOrdemServico);
}
