package geo.track.repository;

import geo.track.domain.Funcionarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionariosRepository extends JpaRepository<Funcionarios, Integer> {
    List<Funcionarios> findByNomeContainingIgnoreCase(String valor);
    Boolean existsByNome (String nome);
    Boolean existsByIdFuncionario (Integer id);
    Funcionarios getByIdFuncionario (Integer id);
}
