package geo.track.jornada;

import geo.track.jornada.request.entrada.RequestConfirmacao;
import geo.track.jornada.request.entrada.RequestEntradaEfetiva;
import geo.track.jornada.request.entrada.RequestEntradaEfetivaSemCadastro;
import geo.track.jornada.response.entrada.RegistroEntradaResponse;
import geo.track.jornada.request.entrada.RequestAgendamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

public interface JornadaSwagger {
    @PostMapping("/agendamento")
    ResponseEntity<RegistroEntradaResponse> agendamentoEntrada(RequestAgendamento request);

    @PatchMapping("/confirmar-entrada")
    ResponseEntity<RegistroEntradaResponse> confirmarEntradaAgendada(RequestConfirmacao request);

    @PostMapping("/entrada-efetiva")
    ResponseEntity<RegistroEntradaResponse> entradaVeiculoEfetiva(RequestEntradaEfetiva request);

    @PostMapping("/entrada-efetiva-sem-cadastro")
    ResponseEntity<RegistroEntradaResponse> entradaVeiculoSemCadastroEfetiva(RequestEntradaEfetivaSemCadastro request);
}
