package geo.track.gestao.veiculo.domain;

import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
import geo.track.gestao.veiculo.application.DeletarVeiculoUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.VeiculoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarVeiculo implements DeletarVeiculoUseCase {
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

