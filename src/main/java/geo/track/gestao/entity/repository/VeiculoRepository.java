package geo.track.gestao.entity.repository;

import geo.track.gestao.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo,Integer> {

    Boolean existsByPlacaIgnoreCase(String placa);
    List<Veiculo> findAllByPlacaStartsWithIgnoreCase(String placa);
    void deleteByPlacaIgnoreCase(String placa);
    @Query("SELECT v FROM Veiculo v WHERE v.fkCliente.idCliente = :idCliente")
    List<Veiculo> findAllByIdCliente(Integer idCliente);
}
