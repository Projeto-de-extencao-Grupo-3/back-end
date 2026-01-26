package geo.track.repository;

import geo.track.domain.ItemServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemServicoRepository extends JpaRepository<ItemServico, Integer> {
    List<ItemServico> findAllByFkOrdemServicoIdOrdemServico(Integer id);
}
