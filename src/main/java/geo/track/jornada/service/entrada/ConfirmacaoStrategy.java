package geo.track.jornada.service.entrada;

import geo.track.enums.os.StatusVeiculo;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.RegistroEntradaExceptionMessages;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.entity.repository.RegistroEntradaRepository;
import geo.track.jornada.service.EntradaJornadaStrategy;
import geo.track.mapper.RegistroEntradaMapper;
import geo.track.service.VeiculoService;
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
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;
    private final VeiculoService VEICULO_SERVICE;

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

        RegistroEntrada entradaAgendada = REGISTRO_ENTRADA_REPOSITORY.findById(requestConfirmacao.fkRegistro())
                .orElseThrow(() -> new DataNotFoundException(RegistroEntradaExceptionMessages.REGISTRO_ENTRADA_NAO_ENCONTRADO, Domains.REGISTRO_ENTRADA));

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
        return REGISTRO_ENTRADA_REPOSITORY.save(entradaAtualizada);
    }
}
