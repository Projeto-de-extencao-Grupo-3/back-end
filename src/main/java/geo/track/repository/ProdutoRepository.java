package geo.track.repository;

import geo.track.domain.Produto;
import geo.track.enums.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto,Integer> {
    List<Produto> findByAtivoTrue();
    Optional<Produto> findByIdProdutoAndAtivoTrue(Integer idProduto);
    boolean existsByIdProdutoAndAtivoTrue(Integer idProduto);

    List<Produto> findByTipoServico(Servico tipoServico);
}
