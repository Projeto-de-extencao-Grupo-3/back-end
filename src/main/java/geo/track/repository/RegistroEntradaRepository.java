package geo.track.repository;

import geo.track.domain.RegistroEntrada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroEntradaRepository extends JpaRepository<RegistroEntrada, Integer> {
    List<RegistroEntrada> findAllByFkVeiculo_IdVeiculo(Long id);
}
