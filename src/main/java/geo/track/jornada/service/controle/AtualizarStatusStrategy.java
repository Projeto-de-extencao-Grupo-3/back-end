package geo.track.jornada.service.controle;

import geo.track.dto.os.request.RequestPatchStatus;
import geo.track.enums.os.StatusVeiculo;
import geo.track.exception.BadBusinessRuleException;
import geo.track.exception.constraint.message.Domains;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.service.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AtualizarStatusStrategy implements ControleJornadaStrategy<OrdemDeServico> {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.ATUALIZAR_STATUS.equals(tipoJornada);
    }

    @Override
    public OrdemDeServico execute(Integer idOrdemServico, GetJornada requestGet) {
        RequestPatchStatus request = (RequestPatchStatus) requestGet;

        LocalDate dataHoje = LocalDate.now();

        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdemServico);
        StatusVeiculo statusAtual = ordem.getStatus();
        StatusVeiculo statusDesejado = request.getStatus();
        StatusVeiculo proximoStatus = StatusVeiculo.values()[statusAtual.ordinal() + 1];

        Boolean serAguardandoAutorizacaoOuVaga = statusAtual.equals(StatusVeiculo.AGUARDANDO_AUTORIZACAO) || statusAtual.equals(StatusVeiculo.AGUARDANDO_VAGA);

        // Valida se a transição de status é valida (permitindo apenas avanço ou retrocesso para AGUARDANDO_AUTORIZACAO e AGUARDANDO_VAGA)
        // Permite apenas avanço para os status seguintes, exceto para AGUARDANDO_AUTORIZACAO e AGUARDANDO_VAGA, que podem retroceder para AGUARDANDO_ORCAMENTO
        if (statusAtual.equals(statusDesejado) ||
                (statusDesejado.ordinal() < statusAtual.ordinal() && !serAguardandoAutorizacaoOuVaga) ||
                (statusDesejado != proximoStatus && statusDesejado.ordinal() > proximoStatus.ordinal()))
            throw new BadBusinessRuleException("Transição de status inválida. O próximo status deve ser: " + proximoStatus, Domains.ORDEM_DE_SERVICO);

        if (statusDesejado.equals(StatusVeiculo.FINALIZADO)) ordem.setDataSaidaEfetiva(dataHoje);
        ordem.setStatus(request.getStatus());
        ordem.setDataAtualizacao(dataHoje);

        return ORDEM_SERVICO_REPOSITORY.save(ordem);
    }
}
