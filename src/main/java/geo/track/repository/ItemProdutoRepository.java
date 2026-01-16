package geo.track.repository;

import geo.track.domain.ItemProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemProdutoRepository extends JpaRepository<ItemProduto, Integer> {
    List<ItemProduto> findAllByFkOrdemServicoIdOrdemServico(Integer id);
}
