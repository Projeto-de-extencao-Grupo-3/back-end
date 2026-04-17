package geo.track.externo.arquivo.infraestructure.persistence;

import geo.track.externo.arquivo.infraestructure.persistence.entity.Metadado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadadoRepository extends JpaRepository<Metadado, Integer> {
}
