package geo.track.jornada.service.usecase.implementation;

import geo.track.domain.Cliente;
import geo.track.domain.Veiculo;
import geo.track.dto.veiculos.request.RequestPostVeiculo;
import geo.track.jornada.service.usecase.CadastrarVeiculoUseCase;
import geo.track.repository.ClienteRepository;
import geo.track.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarVeiculoImplementation implements CadastrarVeiculoUseCase {

    private final VeiculoRepository VEICULO_REPOSITORY;
    private final ClienteRepository CLIENTE_REPOSITORY;

    @Override
    public Veiculo execute(RequestPostVeiculo dto) {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setMarca(dto.getMarca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setPrefixo(dto.getPrefixo());
        veiculo.setAnoModelo(dto.getAnoModelo());

        // 1. Busca o cliente no banco
        Cliente proprietario = CLIENTE_REPOSITORY.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + dto.getIdCliente()));

        // 2. Vincula o objeto cliente ao veículo
        veiculo.setFkCliente(proprietario);

        // 3. Salva usando o repository correto (VEICULO_REPOSITORY)
        return VEICULO_REPOSITORY.save(veiculo);
    }
}
