package geo.track.externo.arquivo.infraestructure.persistence;

import geo.track.externo.arquivo.infraestructure.persistence.entity.Arquivo;
import geo.track.externo.arquivo.infraestructure.persistence.entity.Categoria;
import geo.track.externo.arquivo.infraestructure.persistence.entity.StatusArquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
//    @Query("SELECT a FROM Arquivo a JOIN Metadado m WHERE m.chave .fkOrdemServico = :fkOrdemServico AND a.categoria = :categoria")
//    Optional<Arquivo> findByFkOrdemServicoAndCategoria(Integer fkOrdemServico, Categoria categoria);
}
