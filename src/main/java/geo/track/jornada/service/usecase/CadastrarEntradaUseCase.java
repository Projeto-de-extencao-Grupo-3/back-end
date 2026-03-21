package geo.track.jornada.service.usecase;

import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;

public interface CadastrarEntradaUseCase {
    RegistroEntrada execute(RegistroEntrada entrada);
}
