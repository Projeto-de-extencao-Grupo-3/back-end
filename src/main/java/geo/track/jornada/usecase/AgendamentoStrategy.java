package geo.track.jornada.usecase;

import geo.track.domain.Veiculo;
import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServicoRepository;
import geo.track.jornada.entity.RegistroEntradaRepository;
import geo.track.service.VeiculoService;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.request.entrada.RequestAgendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AgendamentoStrategy implements JornadaStrategy<RequestAgendamento, RegistroEntrada> {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;
    private final VeiculoService VEICULO_SERVICE;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.AGENDAMENTO.equals(tipoJornada);
    }

    @Override
    public RegistroEntrada execute(RequestAgendamento request) {
        StatusVeiculo status = StatusVeiculo.AGUARDANDO_ENTRADA;
        Veiculo veiculo = VEICULO_SERVICE.findVeiculoById(request.fkVeiculo());
        LocalDate dataEntradaPrevista = request.dataEntradaPrevista();

        OrdemDeServico ordemDeServico = this.cadastrarOrdemServico(status);

        return this.cadastrarAgendamento(ordemDeServico, veiculo, dataEntradaPrevista);
    }

    private OrdemDeServico cadastrarOrdemServico(StatusVeiculo status) {
        OrdemDeServico ordemDeServico = new OrdemDeServico();
        ordemDeServico.setStatus(status);
        ordemDeServico.setAtivo(true);

        return ORDEM_SERVICO_REPOSITORY.save(ordemDeServico);
    }

    private RegistroEntrada cadastrarAgendamento(OrdemDeServico ordemDeServico, Veiculo veiculo, LocalDate dataEntradaPrevista) {
        RegistroEntrada registroEntrada = new RegistroEntrada();
        registroEntrada.setFkOrdemServico(ordemDeServico);
        registroEntrada.setFkVeiculo(veiculo);
        registroEntrada.setDataEntradaPrevista(dataEntradaPrevista);

        return REGISTRO_ENTRADA_REPOSITORY.save(registroEntrada);
    }
}
