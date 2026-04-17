package geo.track.jornada.application.controle;

import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;

public interface DefinirNotaFiscalRealizadaUseCase {
    OrdemDeServico execute(Integer idOrdemServico);
}
