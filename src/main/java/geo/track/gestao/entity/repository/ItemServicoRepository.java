package geo.track.gestao.entity.repository;

import geo.track.gestao.entity.ItemServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemServicoRepository extends JpaRepository<ItemServico, Integer> {
    List<ItemServico> findAllByFkOrdemServicoIdOrdemServico(Integer id);
}
