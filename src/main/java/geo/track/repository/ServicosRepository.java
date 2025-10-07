package geo.track.repository;

import geo.track.domain.Funcionarios;
import geo.track.domain.Servicos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicosRepository extends JpaRepository<Servicos, Integer> {
    Servicos getByIdServico (Integer id);
    Boolean existsByIdServico (Integer id);
    Boolean existsByTituloServico (String nome);
}
