package geo.track.jornada.application.entrada;

import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;

import java.time.LocalDate;

public interface AgendamentoUseCase {
    RegistroEntrada execute(LocalDate dataEntradaPrevista, Integer fkVeiculo);
}