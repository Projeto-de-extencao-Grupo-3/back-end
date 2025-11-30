package geo.track.repository;

import geo.track.domain.ItensProdutos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItensProdutosRepository extends JpaRepository<ItensProdutos, Integer> {
    List<ItensProdutos> findAllByFkOrdemServicoIdOrdemServico(Integer id);
}
