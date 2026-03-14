package geo.track.repository;

import geo.track.domain.Metadado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadadoRepository extends JpaRepository<Metadado, Integer> {
}
