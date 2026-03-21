package geo.track.jornada.service.entrada;

import geo.track.enums.os.StatusVeiculo;
import geo.track.exception.DataNotFoundException;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.service.usecase.CadastrarEntradaUseCase;
import geo.track.jornada.util.RegistroEntradaMapper;
import geo.track.service.RegistroEntradaService;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.request.entrada.RequestConfirmacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para CONFIRMACAO DE ENTRADA **/
@Component
@RequiredArgsConstructor
public class ConfirmacaoStrategy implements EntradaJornadaStrategy {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;
    private final CadastrarEntradaUseCase CADASTRAR_ENTRADA_PORT;
    private final RegistroEntradaService REGISTRO_ENTRADA_SERVICE;

    @Override
    public Boolean isApplicable(TipoJornada tipo) {
        return TipoJornada.CONFIRMAR_ENTRADA_AGENDADA.equals(tipo);
    }

    /**
     * Executa confirmação de entrada previamente agendada.
     *
     * @param request deve ser um RequestConfirmacao contendo:
     *                - fkRegistro: ID do registro de entrada agendado
     *                - entrada: dados atualizados da entrada
     * @return RegistroEntrada com dados confirmados
     * @throws DataNotFoundException se o registro não for encontrado
     */
    @Override
    public RegistroEntrada execute(GetJornada request) {
        RequestConfirmacao requestConfirmacao = (RequestConfirmacao) request;

        StatusVeiculo status = StatusVeiculo.AGUARDANDO_ORCAMENTO;
        RegistroEntrada entradaAgendada = REGISTRO_ENTRADA_SERVICE.buscarEntradaPorId(requestConfirmacao.fkRegistro());

        this.atualizarOrdemDeServico(entradaAgendada, status);

        RegistroEntrada entradaAtualizada = RegistroEntradaMapper.toEntityUpdate(entradaAgendada, ((RequestConfirmacao) request).entrada());

        return this.atualizarAgendamento(entradaAtualizada);
    }

    private void atualizarOrdemDeServico(RegistroEntrada entrada, StatusVeiculo status) {
        OrdemDeServico ordemDeServico = entrada.getFkOrdemServico();
        ordemDeServico.setStatus(status);
        ORDEM_SERVICO_REPOSITORY.save(ordemDeServico);
    }


    private RegistroEntrada atualizarAgendamento(RegistroEntrada entradaAtualizada) {
        return CADASTRAR_ENTRADA_PORT.execute(entradaAtualizada);
    }
}
