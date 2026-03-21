package geo.track.jornada.service.usecase.implementation;

import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.service.usecase.CadastrarOrdemServicoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CadastrarOrdemServicoImplementation implements CadastrarOrdemServicoUseCase {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    public geo.track.jornada.entity.OrdemDeServico execute(StatusVeiculo status) {
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
