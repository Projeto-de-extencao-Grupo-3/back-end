package geo.track.repository;

import geo.track.domain.Clientes;
import geo.track.domain.OrdemDeServicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface OrdemDeServicosRepository extends JpaRepository<OrdemDeServicos, Integer> {
}
