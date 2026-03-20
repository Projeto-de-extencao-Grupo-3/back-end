package geo.track.jornada.service.usecase.implementation;

import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.service.usecase.CadastrarOrdemServicoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarOrdemServicoImplementation implements CadastrarOrdemServicoUseCase {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    public geo.track.jornada.entity.OrdemDeServico execute(StatusVeiculo status) {
        geo.track.jornada.entity.OrdemDeServico ordemDeServico = new geo.track.jornada.entity.OrdemDeServico();
        ordemDeServico.setStatus(status);
        ordemDeServico.setAtivo(true);

        return ORDEM_SERVICO_REPOSITORY.save(ordemDeServico);
    }

}
