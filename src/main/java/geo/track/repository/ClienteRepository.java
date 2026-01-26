package geo.track.repository;

import geo.track.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    Optional<Cliente> findByCpfCnpj(String cpfCnpj);

    Boolean existsByNome(String nome);
    Boolean existsByCpfCnpj(String cpfCnpj);
}
