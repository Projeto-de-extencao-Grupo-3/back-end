package geo.track.repository;

import geo.track.domain.ItensServicos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItensServicoRepository extends JpaRepository<ItensServicos, Integer> {
    List<ItensServicos> findAllByFkOrdemServicoIdOrdemServico(Integer id);
}
