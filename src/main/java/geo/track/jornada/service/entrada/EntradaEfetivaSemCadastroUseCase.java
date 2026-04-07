package geo.track.jornada.service.entrada;

import geo.track.dto.veiculos.request.RequestPostVeiculo;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.request.entrada.RequestEntrada;
import geo.track.jornada.request.entrada.RequestEntradaEfetivaSemCadastro;

public interface EntradaEfetivaSemCadastroUseCase {
    RegistroEntrada execute(RequestPostVeiculo requestVeiculo, RequestEntrada requestEntrada);
}