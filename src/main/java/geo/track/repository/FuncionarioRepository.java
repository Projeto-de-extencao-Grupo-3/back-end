package geo.track.repository;

import geo.track.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    List<Funcionario> findByNomeContainingIgnoreCase(String valor);
    Boolean existsByEmail(String email);
    List<Funcionario> findByFkOficina_IdOficina(Integer idOficina);
    Boolean existsByIdFuncionario (Integer id);
    Funcionario getByIdFuncionario (Integer id);

    Optional<Funcionario> findByEmail(String cnpj);

//    @Query("SELECT p FROM Clientes p JOIN p.cliente c WHERE c.idEmpresa = :idEmpresa")
//    List<Funcionarios> buscarIdCliente(@Param("idEmpresa") String idEmpresa);
}
