package geo.track.catalogo.produto.infraestructure.persistence;

import geo.track.catalogo.produto.domain.entity.Produto;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto,Integer> {
    List<Produto> findByAtivoTrue();
    Optional<Produto> findByIdProdutoAndAtivoTrue(Integer idProduto);
    boolean existsByIdProdutoAndAtivoTrue(Integer idProduto);
    List<Produto> findByTipoServico(Servico tipoServico);
    Page<Produto> findByAtivoTrue(Pageable pageable);
    Page<Produto> findByTipoServicoAndAtivoTrue(Servico tipo, Pageable pageable);
}
