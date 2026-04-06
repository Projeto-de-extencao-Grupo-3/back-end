package geo.track.jornada.service.entrada;

import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.request.entrada.RequestEntrada;
import geo.track.jornada.request.entrada.RequestEntradaEfetiva;

public interface EntradaEfetivaUseCase {
    RegistroEntrada execute(Integer fkVeiculo, RequestEntrada request);
}