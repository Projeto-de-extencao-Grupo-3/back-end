package geo.track.gestao.funcionario.infraestructure.persistence;

import geo.track.gestao.funcionario.infraestructure.persistence.entity.Funcionario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    List<Funcionario> findByNomeContainingIgnoreCase(String valor);
    Page<Funcionario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Boolean existsByEmail(String email);
    List<Funcionario> findByFkOficina_IdOficina(Integer idOficina);
    Boolean existsByIdFuncionario (Integer id);
    Funcionario getByIdFuncionario (Integer id);

    Optional<Funcionario> findByEmail(String cnpj);

//    @Query("SELECT p FROM Clientes p JOIN p.cliente c WHERE c.idEmpresa = :idEmpresa")
//    List<Funcionarios> buscarIdCliente(@Param("idEmpresa") String idEmpresa);
}
