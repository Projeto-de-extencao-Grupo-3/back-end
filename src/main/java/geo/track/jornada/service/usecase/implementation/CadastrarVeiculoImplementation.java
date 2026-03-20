package geo.track.jornada.service.usecase.implementation;

import geo.track.domain.Veiculo;
import geo.track.dto.veiculos.request.RequestPostVeiculo;
import geo.track.jornada.service.usecase.CadastrarVeiculoUseCase;
import geo.track.mapper.VeiculoMapper;
import geo.track.repository.VeiculoRepository;
import org.springframework.stereotype.Component;

@Component
public class CadastrarVeiculoImplementation implements CadastrarVeiculoUseCase {
    private final VeiculoRepository VEICULO_REPOSITORY;

    public CadastrarVeiculoImplementation(VeiculoRepository ordemDeServicoRepository) {
        this.VEICULO_REPOSITORY = ordemDeServicoRepository;
    }

    public Veiculo execute(RequestPostVeiculo request) {
        Veiculo veiculo = VeiculoMapper.toEntity(request);
        return VEICULO_REPOSITORY.save(veiculo);
    }

}
