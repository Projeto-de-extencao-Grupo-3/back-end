package geo.track.jornada.application.entrada;

import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import geo.track.jornada.infraestructure.request.entrada.RequestEntrada;

public interface EntradaEfetivaUseCase {
    RegistroEntrada execute(Integer fkVeiculo, RequestEntrada request);
}