package geo.track.gestao.entity.repository;

import geo.track.gestao.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByAtivoTrue();
    Optional<Cliente> findByIdClienteAndAtivoTrue(Integer idCliente);

    List<Cliente> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);
    List<Cliente> findByNomeContainingIgnoreCaseAndCpfCnpjContainingIgnoreCaseAndAtivoTrue(String nome, String cpfCnpj);
    List<Cliente> findByCpfCnpjContainingIgnoreCaseAndAtivoTrue(String cpfCnpj);

    Boolean existsByCpfCnpjAndAtivoTrue(String cpfCnpj);

    Optional<Cliente> findByVeiculoPlacaAndAtivoTrue(String placa);
}
