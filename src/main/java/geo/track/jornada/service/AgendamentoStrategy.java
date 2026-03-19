package geo.track.jornada.service;

import geo.track.domain.Veiculo;
import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServicoRepository;
import geo.track.jornada.entity.RegistroEntradaRepository;
import geo.track.jornada.service.usecase.CadastrarOrdemServicoUseCase;
import geo.track.service.VeiculoService;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.request.entrada.RequestAgendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AgendamentoStrategy implements JornadaStrategy<RequestAgendamento, RegistroEntrada> {
    private final CadastrarOrdemServicoUseCase CADASTRAR_OS_PORT;
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;
    private final VeiculoService VEICULO_SERVICE;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.AGENDAMENTO.equals(tipoJornada);
    }

    @Override
    public RegistroEntrada execute(RequestAgendamento request) {
        StatusVeiculo status = StatusVeiculo.AGUARDANDO_ENTRADA;
        OrdemDeServico ordemDeServico = CADASTRAR_OS_PORT.execute(status);
        Veiculo veiculo = VEICULO_SERVICE.findVeiculoById(request.fkVeiculo());

        LocalDate dataEntradaPrevista = request.dataEntradaPrevista();

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
