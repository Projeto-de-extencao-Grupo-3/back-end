package geo.track.repository;

import geo.track.domain.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {
    Servico getByIdServico (Integer id);
    Boolean existsByIdServico (Integer id);
    Boolean existsByTituloServico (String nome);
}
