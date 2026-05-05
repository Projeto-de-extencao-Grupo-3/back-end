package geo.track.gestao.cliente.infraestructure.persistence;

import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByAtivoTrue();
    Optional<Cliente> findByIdClienteAndAtivoTrue(Integer idCliente);

    List<Cliente> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);
    Page<Cliente> findByNomeContainingIgnoreCaseAndAtivoTrue(Pageable pageable, String nome);

    List<Cliente> findByNomeContainingIgnoreCaseAndCpfCnpjContainingIgnoreCaseAndAtivoTrue(String nome, String cpfCnpj);
    List<Cliente> findByCpfCnpjContainingIgnoreCaseAndAtivoTrue(String cpfCnpj);

    Boolean existsByCpfCnpjAndAtivoTrue(String cpfCnpj);

    @Query("SELECT c FROM Cliente c JOIN c.veiculos v WHERE v.placa = :placa AND c.ativo = true")
    Optional<Cliente> findByVeiculoPlacaAndAtivoTrue(String placa);

    Page<Cliente> findAllByAtivoTrue(Pageable pageable);
}
