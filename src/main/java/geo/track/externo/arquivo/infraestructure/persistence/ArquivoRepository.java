package geo.track.externo.arquivo.infraestructure.persistence;

import geo.track.externo.arquivo.infraestructure.persistence.entity.Arquivo;
import geo.track.externo.arquivo.infraestructure.persistence.entity.StatusArquivo;
import geo.track.externo.arquivo.infraestructure.persistence.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
    Optional<Arquivo> findByFkOrdemServicoAndTemplate(Integer fkOrdemServico, Template template);
    Optional<Arquivo> findByFkOrdemServicoAndStatus(Integer fkOrdemServico, StatusArquivo status);

    Optional<Arquivo> findByFkOrdemServico(Integer fkOrdemServico);

    Boolean existsByFkOrdemServicoAndTemplate(Integer fkOrdemServico, Template template);
}
