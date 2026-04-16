package geo.track.jornada.service.entrada.implementation;

import geo.track.jornada.enums.Status;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.request.entrada.RequestEntrada;
import geo.track.jornada.service.entrada.ConfirmacaoUseCase;
import geo.track.jornada.service.usecase.CadastrarEntradaUseCase;
import geo.track.jornada.util.RegistroEntradaMapper;
import geo.track.jornada.service.registroEntrada.RegistroEntradaService;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.request.entrada.RequestConfirmacao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para CONFIRMACAO DE ENTRADA **/
@Component
@RequiredArgsConstructor
public class Confirmacao implements ConfirmacaoUseCase {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;
    private final CadastrarEntradaUseCase CADASTRAR_ENTRADA_PORT;
    private final RegistroEntradaService REGISTRO_ENTRADA_SERVICE;

    @Transactional
    @Override
    public RegistroEntrada execute(Integer fkRegistro, RequestEntrada requestEntrada) {

        Status status = Status.AGUARDANDO_ORCAMENTO;
        RegistroEntrada entradaAgendada = REGISTRO_ENTRADA_SERVICE.buscarEntradaPorId(fkRegistro);

        this.atualizarOrdemDeServico(entradaAgendada, status);

        RegistroEntrada entradaAtualizada = RegistroEntradaMapper.toEntityUpdate(entradaAgendada, requestEntrada);

        return this.atualizarAgendamento(entradaAtualizada);
    }

    private void atualizarOrdemDeServico(RegistroEntrada entrada, Status status) {
        OrdemDeServico ordemDeServico = entrada.getFkOrdemServico();
        ordemDeServico.setStatus(status);
        ORDEM_SERVICO_REPOSITORY.save(ordemDeServico);
    }


    private RegistroEntrada atualizarAgendamento(RegistroEntrada entradaAtualizada) {
        return CADASTRAR_ENTRADA_PORT.execute(entradaAtualizada);
    }
}
