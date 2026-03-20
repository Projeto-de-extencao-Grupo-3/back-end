package geo.track.jornada.service.usecase.implementation;

import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServicoRepository;
import geo.track.jornada.service.usecase.CadastrarOrdemServicoUseCase;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class CadastrarOrdemServicoImplementation implements CadastrarOrdemServicoUseCase {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    public CadastrarOrdemServicoImplementation(geo.track.jornada.entity.OrdemDeServicoRepository ordemDeServicoRepository) {
        this.ORDEM_SERVICO_REPOSITORY = ordemDeServicoRepository;
    }

    public geo.track.jornada.entity.OrdemDeServico execute(StatusVeiculo status) {
        geo.track.jornada.entity.OrdemDeServico ordemDeServico = new geo.track.jornada.entity.OrdemDeServico();
        ordemDeServico.setStatus(status);
        ordemDeServico.setAtivo(true);
        ordemDeServico.setValorTotal(0.0);
        ordemDeServico.setValorTotalProdutos(0.0);
        ordemDeServico.setValorTotalServicos(0.0);
        ordemDeServico.setAtivo(true);
        ordemDeServico.setDataAtualizacao(LocalDate.now());

        return ORDEM_SERVICO_REPOSITORY.save(ordemDeServico);
    }

}
