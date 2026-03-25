package geo.track.jornada.service.entrada;

import geo.track.gestao.entity.Veiculo;
import geo.track.jornada.enums.Status;
import geo.track.jornada.entity.repository.RegistroEntradaRepository;
import geo.track.jornada.service.usecase.CadastrarOrdemServicoUseCase;
import geo.track.gestao.service.VeiculoService;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.request.entrada.RequestAgendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
/** Strategy para AGENDAMENTO DE ENTRADA **/
@Component
@RequiredArgsConstructor
public class AgendamentoStrategy implements EntradaJornadaStrategy {
    private final CadastrarOrdemServicoUseCase CADASTRAR_OS_PORT;
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;
    private final VeiculoService VEICULO_SERVICE;

    @Override
    public Boolean isApplicable(TipoJornada tipo) {
        return TipoJornada.AGENDAMENTO.equals(tipo);
    }

    /**
     * Executa o agendamento de entrada do veículo.
     *
     * @param request deve ser um RequestAgendamento contendo:
     *                - fkVeiculo: ID do veículo
     *                - dataEntradaPrevista: data prevista para a entrada
     * @return RegistroEntrada com o agendamento criado
     */
    @Override
    public RegistroEntrada execute(GetJornada request) {
        RequestAgendamento requestAgendamento = (RequestAgendamento) request;

        Status status = Status.AGUARDANDO_ENTRADA;
        OrdemDeServico ordemDeServico = CADASTRAR_OS_PORT.execute(status);
        Veiculo veiculo = VEICULO_SERVICE.findVeiculoById(requestAgendamento.fkVeiculo());

        LocalDate dataEntradaPrevista = requestAgendamento.dataEntradaPrevista();

        return this.cadastrarAgendamento(ordemDeServico, veiculo, dataEntradaPrevista);
    }

    private RegistroEntrada cadastrarAgendamento(OrdemDeServico ordemDeServico, Veiculo veiculo, LocalDate dataEntradaPrevista) {
        RegistroEntrada registroEntrada = new RegistroEntrada();
        registroEntrada.setFkOrdemServico(ordemDeServico);
        registroEntrada.setFkVeiculo(veiculo);
        registroEntrada.setDataEntradaPrevista(dataEntradaPrevista);

        return REGISTRO_ENTRADA_REPOSITORY.save(registroEntrada);
    }
}
