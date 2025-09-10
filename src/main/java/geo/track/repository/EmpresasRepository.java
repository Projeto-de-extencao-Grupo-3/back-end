package geo.track.repository;

import geo.track.domain.Empresas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpresasRepository extends JpaRepository<Empresas, Integer> {
    List<Empresas> findByrazaoSocialContainingIgnoreCase(String valor);
    Optional<Empresas> findBycnpj(String valor);

}
