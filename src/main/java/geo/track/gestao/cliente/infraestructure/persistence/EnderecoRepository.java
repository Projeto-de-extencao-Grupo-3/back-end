package geo.track.gestao.cliente.infraestructure.persistence;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    Boolean existsByCep(String cep);
}
