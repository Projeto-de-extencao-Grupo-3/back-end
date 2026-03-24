package geo.track.external.entity.repository;

import geo.track.external.entity.Arquivo;
import geo.track.enums.StatusArquivo;
import geo.track.enums.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
    Optional<Arquivo> findByFkOrdemServicoAndTemplate(Integer fkOrdemServico, Template template);
    Optional<Arquivo> findByFkOrdemServicoAndStatus(Integer fkOrdemServico, StatusArquivo status);

    Optional<Arquivo> findByFkOrdemServico(Integer fkOrdemServico);

    Boolean existsByFkOrdemServicoAndTemplate(Integer fkOrdemServico, Template template);
}
