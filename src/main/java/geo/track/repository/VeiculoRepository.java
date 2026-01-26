package geo.track.repository;

import geo.track.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo,Integer> {

    Boolean existsByPlacaIgnoreCase(String placa);
    List<Veiculo> findAllByPlacaStartsWithIgnoreCase(String placa);
    void deleteByPlacaIgnoreCase(String placa);
}
