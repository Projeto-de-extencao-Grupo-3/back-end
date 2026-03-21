package geo.track.repository;

import geo.track.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    List<Cliente> findByNomeContainingIgnoreCaseAndCpfCnpjContainingIgnoreCase(String nome, String cpfCnpj);
    List<Cliente> findByCpfCnpjContainingIgnoreCase(String cpfCnpj);

    Boolean existsByNome(String nome);
    Boolean existsByCpfCnpj(String cpfCnpj);

    Optional<Cliente> findByVeiculoPlaca(String placa);
}
