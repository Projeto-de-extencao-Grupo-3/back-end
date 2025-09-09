package geo.track.repository;

import geo.track.domain.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientesRepository extends JpaRepository<Clientes, Integer> {

    List<Clientes> findByNome(String nome);
    Optional<Clientes> findByCpfCnpj(String cpfCnpj);

    Boolean existsByNome(String nome);
    Boolean existsByCpfCnpj(String cpfCnpj);
}
