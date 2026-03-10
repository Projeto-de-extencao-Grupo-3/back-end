package geo.track.repository;

import geo.track.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    List<Funcionario> findByNomeContainingIgnoreCase(String valor);
    List<Funcionario> findByFkOficina_IdOficina(Integer idOficina);
    Funcionario getByIdFuncionario (Integer id);
    Boolean existsByEmail(String email);
    Boolean existsByNome(String nome);
    Boolean existsByIdFuncionario (Integer id);
    Optional<Funcionario> findByEmail(String cnpj);

//    @Query("SELECT p FROM Clientes p JOIN p.cliente c WHERE c.idEmpresa = :idEmpresa")
//    List<Funcionarios> buscarIdCliente(@Param("idEmpresa") String idEmpresa);
}
