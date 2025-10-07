package geo.track.repository;

import geo.track.domain.Clientes;
import geo.track.domain.OrdemDeServicos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdemDeServicosRepository extends JpaRepository<OrdemDeServicos, Integer> {
    List<OrdemDeServicos> findByVeiculo(Integer fkVeiculo);

    Boolean existsByVeiculo(Integer fkVeiculo);
}
