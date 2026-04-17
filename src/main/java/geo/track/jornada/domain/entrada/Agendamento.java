package geo.track.jornada.domain.entrada;

import geo.track.gestao.veiculo.domain.VeiculoService;
import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.jornada.application.CadastrarOrdemServicoUseCase;
import geo.track.jornada.application.entrada.AgendamentoUseCase;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import geo.track.jornada.infraestructure.persistence.RegistroEntradaRepository;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public RegistroEntrada execute(LocalDate dataEntradaPrevista, Integer fkVeiculo) {

        Status status = Status.AGUARDANDO_ENTRADA;
        OrdemDeServico ordemDeServico = CADASTRAR_OS_PORT.execute(status);
        Veiculo veiculo = VEICULO_SERVICE.buscarVeiculoPeloId(fkVeiculo);

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
