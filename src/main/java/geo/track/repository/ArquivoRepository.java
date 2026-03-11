package geo.track.repository;

import geo.track.domain.Arquivo;
import geo.track.enums.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
    Optional<Arquivo> findByFkOrdemServicoAndTemplate(Integer fkOrdemServico, Template template);
}
