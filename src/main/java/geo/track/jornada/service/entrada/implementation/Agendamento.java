package geo.track.jornada.service.entrada.implementation;

import geo.track.gestao.entity.Veiculo;
import geo.track.jornada.enums.Status;
import geo.track.jornada.entity.repository.RegistroEntradaRepository;
import geo.track.jornada.service.entrada.AgendamentoUseCase;
import geo.track.jornada.service.usecase.CadastrarOrdemServicoUseCase;
import geo.track.gestao.service.VeiculoService;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.request.entrada.RequestAgendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
/** Strategy para AGENDAMENTO DE ENTRADA **/
@Component
@RequiredArgsConstructor
public class Agendamento implements AgendamentoUseCase {
    private final CadastrarOrdemServicoUseCase CADASTRAR_OS_PORT;
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;
    private final VeiculoService VEICULO_SERVICE;

    @Override
    public RegistroEntrada execute(LocalDate dataEntradaPrevista, Integer fkVeiculo) {

        Status status = Status.AGUARDANDO_ENTRADA;
        OrdemDeServico ordemDeServico = CADASTRAR_OS_PORT.execute(status);
        Veiculo veiculo = VEICULO_SERVICE.findVeiculoById(fkVeiculo);

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
