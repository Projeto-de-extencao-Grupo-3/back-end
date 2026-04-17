package geo.track.jornada.application.entrada;

import geo.track.gestao.veiculo.infraestructure.request.RequestPostVeiculo;
import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import geo.track.jornada.infraestructure.request.entrada.RequestEntrada;

public interface EntradaEfetivaSemCadastroUseCase {
    RegistroEntrada execute(RequestPostVeiculo requestVeiculo, RequestEntrada requestEntrada);
}