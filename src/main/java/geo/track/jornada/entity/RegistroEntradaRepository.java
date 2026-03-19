package geo.track.jornada.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistroEntradaRepository extends JpaRepository<RegistroEntrada, Integer> {
    // Existing method, might need adaptation if fkVeiculo_IdVeiculo is not unique per office
    List<RegistroEntrada> findAllByFkVeiculo_IdVeiculo(Long id);

    @Query("SELECT CASE WHEN COUNT(re) > 0 THEN TRUE ELSE FALSE END FROM RegistroEntrada re JOIN re.fkOrdemServico os WHERE os.status <> StatusVeiculo.FINALIZADO AND re.fkVeiculo.idVeiculo = :idVeiculo")
    Boolean existsOrdensNaoFinalizadas(@Param("idVeiculo") Integer idVeiculo);
}
