package geo.track.jornada.service;

import geo.track.enums.os.StatusVeiculo;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.RegistroEntradaExceptionMessages;
import geo.track.mapper.RegistroEntradaMapper;
import geo.track.jornada.entity.OrdemDeServicoRepository;
import geo.track.jornada.entity.RegistroEntradaRepository;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.request.entrada.RequestConfirmacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmacaoStrategy implements JornadaStrategy<RequestConfirmacao, RegistroEntrada> {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.CONFIRMAR_ENTRADA_AGENDADA.equals(tipoJornada);
    }

    @Override
    public RegistroEntrada execute(RequestConfirmacao request) {
        StatusVeiculo status = StatusVeiculo.AGUARDANDO_ORCAMENTO;

        RegistroEntrada entradaAgendada = REGISTRO_ENTRADA_REPOSITORY.findById(request.fkRegistro())
                .orElseThrow(() -> new DataNotFoundException(RegistroEntradaExceptionMessages.REGISTRO_ENTRADA_NAO_ENCONTRADO, Domains.REGISTRO_ENTRADA));

        this.atualizarOrdemDeServico(entradaAgendada, status);

        RegistroEntrada entradaAtualizada = RegistroEntradaMapper.toEntityUpdate(entradaAgendada, request.entrada());

        return this.atualizarAgendamento(entradaAtualizada );
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
