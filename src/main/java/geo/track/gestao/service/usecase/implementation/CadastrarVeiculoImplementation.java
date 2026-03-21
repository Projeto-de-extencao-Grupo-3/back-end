package geo.track.gestao.service.usecase.implementation;

import geo.track.entity.Veiculo;
import geo.track.gestao.service.usecase.CadastrarVeiculoUseCase;
import geo.track.repository.VeiculoRepository;
import org.springframework.stereotype.Component;

@Component
public class CadastrarVeiculoImplementation implements CadastrarVeiculoUseCase {
    private final VeiculoRepository VEICULO_REPOSITORY;

    public CadastrarVeiculoImplementation(VeiculoRepository ordemDeServicoRepository) {
        this.VEICULO_REPOSITORY = ordemDeServicoRepository;
    }

    public Veiculo execute(Veiculo request) {
        return VEICULO_REPOSITORY.save(request);
    }

}
