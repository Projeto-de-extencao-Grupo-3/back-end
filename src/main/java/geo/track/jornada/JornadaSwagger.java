package geo.track.jornada;

import geo.track.jornada.request.entrada.RequestConfirmacao;
import geo.track.jornada.request.entrada.RequestEntradaEfetivaSemCadastro;
import geo.track.jornada.request.entrada.RequestEntradaEfetiva;
import geo.track.jornada.response.entrada.RegistroEntradaResponse;
import geo.track.jornada.request.entrada.RequestAgendamento;
import org.springframework.http.ResponseEntity;

public interface JornadaSwagger {
    ResponseEntity<RegistroEntradaResponse> agendamentoEntrada(RequestAgendamento request);

    ResponseEntity<RegistroEntradaResponse> confirmarEntradaAgendada(RequestConfirmacao request);

    ResponseEntity<RegistroEntradaResponse> entradaVeiculoEfetiva(RequestEntradaEfetiva request);

    ResponseEntity<RegistroEntradaResponse> entradaVeiculoSemCadastroEfetiva(RequestEntradaEfetivaSemCadastro request);
}
