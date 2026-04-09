package geo.track.gestao.entity.repository;

import geo.track.gestao.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    Boolean existsByCep(String cep);
}
