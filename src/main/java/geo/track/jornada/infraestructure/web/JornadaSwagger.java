package geo.track.jornada.infraestructure.web;

import geo.track.jornada.infraestructure.request.entrada.RequestConfirmacao;
import geo.track.jornada.infraestructure.request.entrada.RequestEntradaEfetivaSemCadastro;
import geo.track.jornada.infraestructure.request.entrada.RequestEntradaEfetiva;
import geo.track.jornada.infraestructure.response.entrada.RegistroEntradaResponse;
import geo.track.jornada.infraestructure.request.entrada.RequestAgendamento;
import org.springframework.http.ResponseEntity;

public interface JornadaSwagger {
    ResponseEntity<RegistroEntradaResponse> agendamentoEntrada(RequestAgendamento request);

    ResponseEntity<RegistroEntradaResponse> confirmarEntradaAgendada(RequestConfirmacao request);

    ResponseEntity<RegistroEntradaResponse> entradaVeiculoEfetiva(RequestEntradaEfetiva request);

    ResponseEntity<RegistroEntradaResponse> entradaVeiculoSemCadastroEfetiva(RequestEntradaEfetivaSemCadastro request);
}
