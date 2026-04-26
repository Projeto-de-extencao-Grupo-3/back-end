package geo.track.externo.arquivo.infraestructure.persistence;

import geo.track.externo.arquivo.infraestructure.persistence.entity.Arquivo;
import geo.track.externo.arquivo.infraestructure.persistence.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
    @Query("SELECT a FROM Arquivo a JOIN a.metadados m where a.categoria = :categoria AND m.chave = 'anoMesReferencia' AND m.valor = :dataFormat AND a.oficina.idOficina = :idOficina")
    Optional<Arquivo> findRelatorioByFkOficinaAndAnoMesReferencia(@Param("categoria") Categoria categoria, @Param("idOficina") Integer idOficina, @Param("dataFormat") String dataFormat);

    @Query("SELECT a FROM Arquivo a JOIN a.metadados m WHERE m.chave = 'fkOrdemServico' AND m.valor = :fkOrdemServico AND a.categoria = :categoria")
    Optional<Arquivo> findByFkOrdemServicoAndCategoria(Integer fkOrdemServico, Categoria categoria);
}
