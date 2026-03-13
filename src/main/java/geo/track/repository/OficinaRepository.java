package geo.track.repository;

import geo.track.domain.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OficinaRepository extends JpaRepository<Oficina, Integer> {
    List<Oficina> findByrazaoSocialContainingIgnoreCase(String valor);
    Optional<Oficina> findByCnpj(String cnpj);


}
