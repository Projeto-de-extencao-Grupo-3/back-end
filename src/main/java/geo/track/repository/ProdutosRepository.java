package geo.track.repository;

import geo.track.domain.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produtos,Integer> {

}
