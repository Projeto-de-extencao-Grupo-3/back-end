package geo.track.jornada.service.controle;

import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.request.controle.RequestPatchSaidaPrevista;

import java.time.LocalDate;

public interface DefinirDataSaidaPrevistaUseCase {
    OrdemDeServico execute(Integer idOrdemServico, LocalDate dataSaidaPrevista);
}
