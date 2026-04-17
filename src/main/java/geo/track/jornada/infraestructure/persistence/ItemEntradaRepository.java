package geo.track.jornada.infraestructure.persistence;

import geo.track.jornada.infraestructure.persistence.entity.ItemEntrada;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.response.listagem.ViewNotaFiscal;
import geo.track.jornada.infraestructure.response.listagem.ViewPagtoPendente;
import geo.track.jornada.infraestructure.response.listagem.ViewPagtoRealizado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemEntradaRepository extends JpaRepository<ItemEntrada, Integer> {
}
