package geo.track.repository;

import geo.track.domain.Empresas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresasRepository extends JpaRepository<Empresas, Integer> {
}
