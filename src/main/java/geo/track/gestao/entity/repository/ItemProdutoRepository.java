package geo.track.gestao.entity.repository;

import geo.track.gestao.entity.ItemProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemProdutoRepository extends JpaRepository<ItemProduto, Integer> {
    List<ItemProduto> findAllByFkOrdemServicoIdOrdemServico(Integer id);
}
