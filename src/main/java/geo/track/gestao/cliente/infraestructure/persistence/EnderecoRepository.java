package geo.track.gestao.cliente.infraestructure.persistence;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    Boolean existsByCep(String cep);

    Boolean existsByCepAndNumero(String cep, Integer numero);

    Optional<Endereco> findByIdEnderecoAndFkCliente_IdCliente(Integer idEndereco, Integer idCliente);
}
