package geo.track.repository;

import geo.track.domain.Oficinas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OficinaRepository extends JpaRepository<Oficinas, Integer> {
    List<Oficinas> findByrazaoSocialContainingIgnoreCase(String valor);
    Optional<Oficinas> findByCnpj(String cnpj);


}
