package geo.track.catalogo.item_produto.infraestructure.persistence;

import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemProdutoRepository extends JpaRepository<ItemProduto, Integer> {
    List<ItemProduto> findAllByFkOrdemServicoIdOrdemServico(Integer id);
}
