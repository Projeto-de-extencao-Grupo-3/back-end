package geo.track.jornada.domain.entrada;

import geo.track.jornada.application.CadastrarEntradaUseCase;
import geo.track.jornada.application.entrada.ConfirmacaoUseCase;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.request.entrada.RequestEntrada;
import geo.track.jornada.domain.RegistroEntradaService;
import geo.track.jornada.infraestructure.mapper.RegistroEntradaMapper;
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
