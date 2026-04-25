package geo.track.gestao.funcionario.domain;

import geo.track.gestao.funcionario.infraestructure.persistence.entity.Funcionario;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.FuncionarioExceptionMessages;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.funcionario.infraestructure.persistence.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository FUNCIONARIO_REPOSITORY;
    private final Log log;

    public List<Funcionario> listarFuncionarios() {
        log.info("Buscando lista completa de funcionarios no banco de dados.");
        return FUNCIONARIO_REPOSITORY.findAll();
    }

    public Page<Funcionario> listarFuncionariosPorNomePaginado(Pageable pageable, String nome){
        return FUNCIONARIO_REPOSITORY.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Funcionario> listarFuncionariosPaginados(Pageable pageable){
        log.info("Buscando lista completa de funcionarios paginados.");
        return FUNCIONARIO_REPOSITORY.findAll(pageable);
    }

    public List<Funcionario> buscarPorOficina(Integer idOficina) {
        log.info("Buscando funcionarios vinculados a oficina ID: {}", idOficina);
        return FUNCIONARIO_REPOSITORY.findByFkOficina_IdOficina(idOficina);
    }

    public Funcionario buscarPorId(Integer id) {
        log.info("Buscando funcionario por ID: {}", id);
        if (!FUNCIONARIO_REPOSITORY.existsByIdFuncionario(id)) {
            log.error("Funcionario com ID {} nao encontrado", id);
            throw new DataNotFoundException(FuncionarioExceptionMessages.FUNCIONARIO_NAO_ENCONTRADO_GENERICO, Domains.FUNCIONARIO);
        }
        return FUNCIONARIO_REPOSITORY.getByIdFuncionario(id);
    }

}
