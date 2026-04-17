package geo.track.jornada.application.controle;

import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;

import java.time.LocalDate;

public interface DefinirDataSaidaPrevistaUseCase {
    OrdemDeServico execute(Integer idOrdemServico, LocalDate dataSaidaPrevista);
}
