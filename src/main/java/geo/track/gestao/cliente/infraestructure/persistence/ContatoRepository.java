package geo.track.gestao.cliente.infraestructure.persistence;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {
    List<Contato> findAllByFkCliente_IdCliente(Integer idCliente);

    Optional<Contato> findByIdContatoAndFkCliente_IdCliente(Integer idContato, Integer idCliente);

    Boolean existsByEmailIgnoreCaseAndFkCliente_IdCliente(String email, Integer idCliente);

    Boolean existsByTelefoneAndFkCliente_IdCliente(String telefone, Integer idCliente);

    Boolean existsByEmailIgnoreCaseAndFkCliente_IdClienteAndIdContatoNot(String email, Integer idCliente, Integer idContato);

    Boolean existsByTelefoneAndFkCliente_IdClienteAndIdContatoNot(String telefone, Integer idCliente, Integer idContato);
}

