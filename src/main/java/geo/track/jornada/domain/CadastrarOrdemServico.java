package geo.track.jornada.domain;

import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.application.CadastrarOrdemServicoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CadastrarOrdemServico implements CadastrarOrdemServicoUseCase {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    public geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico execute(Status status, Integer fkVeiculo) {

        ORDEM_SERVICO_SERVICE.existeOrdemServicoAbertaPorVeiculo(fkVeiculo);

        OrdemDeServico ordemDeServico = new OrdemDeServico();
        ordemDeServico.setStatus(status);
        ordemDeServico.setAtivo(true);
        ordemDeServico.setDataAtualizacao(LocalDate.now());

        ordemDeServico.setNfRealizada(false);
        ordemDeServico.setPagtRealizado(false);
        ordemDeServico.setSeguradora(false);

        ordemDeServico.setValorTotal(0.0);
        ordemDeServico.setValorTotalServicos(0.0);
        ordemDeServico.setValorTotalProdutos(0.0);

        return ORDEM_SERVICO_REPOSITORY.save(ordemDeServico);
    }
}
