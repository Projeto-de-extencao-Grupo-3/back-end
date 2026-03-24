package geo.track.gestao.service.veiculo.implementations;

import geo.track.gestao.entity.Veiculo;
import geo.track.gestao.entity.repository.VeiculoRepository;
import geo.track.dto.veiculos.request.RequestPatchPlaca;
import geo.track.gestao.service.veiculo.AlterarPlacaVeiculoUseCase;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.VeiculoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlterarPlacaVeiculoImplementation implements AlterarPlacaVeiculoUseCase {
    private final VeiculoRepository VEICULO_REPOSITORY;
    private final Log log;

    public Veiculo execute(RequestPatchPlaca body) {
        log.info("Solicitação de alteração de placa para o veículo ID: {}", body.getIdVeiculo());
        Optional<Veiculo> veiculoOpt = VEICULO_REPOSITORY.findById(body.getIdVeiculo());

        if(veiculoOpt.isEmpty()){
            log.error("Falha ao alterar placa: Veículo ID {} não encontrado", body.getIdVeiculo());
            throw new DataNotFoundException(VeiculoExceptionMessages.VEICULO_NAO_ENCONTRADO_ID, Domains.VEICULO);
        }

        Veiculo veiculo = veiculoOpt.get();

        if(VEICULO_REPOSITORY.existsByPlacaIgnoreCase(body.getPlaca())){
            log.warn("Falha ao alterar placa: Nova placa {} já está em uso", body.getPlaca());
            throw new ConflictException(VeiculoExceptionMessages.PLACA_JA_EXISTE_OUTRO_VEICULO, Domains.VEICULO);
        }

        veiculo.setPlaca(body.getPlaca());
        Veiculo atualizado = VEICULO_REPOSITORY.save(veiculo);
        log.info("Placa do veículo ID {} atualizada para {} com sucesso", atualizado.getIdVeiculo(), atualizado.getPlaca());
        return atualizado;
    }
}

