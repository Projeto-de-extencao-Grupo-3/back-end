package geo.track.gestao.service.veiculo.implementations;

import geo.track.gestao.entity.repository.VeiculoRepository;
import geo.track.gestao.service.veiculo.DeletarVeiculoUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.VeiculoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarVeiculoImplementation implements DeletarVeiculoUseCase {
    private final VeiculoRepository VEICULO_REPOSITORY;
    private final Log log;

    public void execute(Integer id) {
        log.info("Solicitação de exclusão do veículo ID: {}", id);
        if(!VEICULO_REPOSITORY.existsById(id)){
            log.error("Falha ao excluir: Veículo ID {} não encontrado", id);
            throw new DataNotFoundException(VeiculoExceptionMessages.VEICULO_NAO_ENCONTRADO_ID, Domains.VEICULO);
        }

        VEICULO_REPOSITORY.deleteById(id);
        log.info("Veículo ID {} excluído com sucesso", id);
    }
}

