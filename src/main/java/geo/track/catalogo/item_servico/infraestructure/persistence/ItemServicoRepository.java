package geo.track.catalogo.item_servico.infraestructure.persistence;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ItemServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemServicoRepository extends JpaRepository<ItemServico, Integer> {
    List<ItemServico> findAllByFkOrdemServicoIdOrdemServico(Integer id);
}
