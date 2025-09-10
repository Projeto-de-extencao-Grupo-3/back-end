package geo.track.repository;

import geo.track.domain.Veiculos;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VeiculosRepository extends JpaRepository<Veiculos,Integer> {

    Boolean existsByPlacaIgnoreCase(String placa);
    List<Veiculos> findAllByPlacaStartsWithIgnoreCase(String placa);
    void deleteByPlacaIgnoreCase(String placa);
}
