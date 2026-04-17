package geo.track.gestao.veiculo.domain;

import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
import geo.track.gestao.veiculo.infraestructure.request.RequestPatchPlaca;
import geo.track.gestao.veiculo.application.AlterarPlacaVeiculoUseCase;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.VeiculoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlterarPlacaVeiculo implements AlterarPlacaVeiculoUseCase {
    private final VeiculoRepository VEICULO_REPOSITORY;
    private final VeiculoService VEICULO_SERVICE;
    private final Log log;

    public Veiculo execute(RequestPatchPlaca body) {
        log.info("Solicitação de alteração de placa para o veículo ID: {}", body.getIdVeiculo());
        Veiculo veiculo = VEICULO_SERVICE.buscarVeiculoPeloId(body.getIdVeiculo());

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

