package geo.track.gestao.funcionario.infraestructure;

import geo.track.gestao.funcionario.infraestructure.persistence.entity.Funcionario;
import geo.track.gestao.funcionario.infraestructure.request.RequestPostFuncionario;
import geo.track.gestao.funcionario.infraestructure.request.RequestPutFuncionario;
import geo.track.gestao.funcionario.infraestructure.response.FuncionarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FuncionarioMapper {
    @Autowired
    static PasswordEncoder passwordEncoder;

    public static FuncionarioResponse toResponse(Funcionario entity) {
        if (entity == null) {
            return null;
        }

        FuncionarioResponse response = new FuncionarioResponse();
        response.setIdFuncionario(entity.getIdFuncionario());
        response.setNome(entity.getNome());
        response.setCargo(entity.getCargo());
        response.setEspecialidade(entity.getEspecialidade());
        response.setTelefone(entity.getTelefone());

        if (entity.getFkOficina() != null) {
            response.setIdOficina(entity.getFkOficina().getIdOficina());
        }

        return response;
    }

    public static List<FuncionarioResponse> toResponse(List<Funcionario> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(FuncionarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static Funcionario toEntity(RequestPostFuncionario body) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(body.getNome());
        funcionario.setCargo(body.getCargo());
        funcionario.setEspecialidade(body.getEspecialidade());
        funcionario.setTelefone(body.getTelefone());
        funcionario.setSenha(body.getSenha());
        funcionario.setEmail(body.getEmail());
        return funcionario;
    }

    public static Funcionario toEntityUpdate(Funcionario funcionario, RequestPutFuncionario body) {
        if (body.getNome() != null) {
            funcionario.setNome(body.getNome());
        }
        if (body.getCargo() != null) {
            funcionario.setCargo(body.getCargo());
        }
        if (body.getEspecialidade() != null) {
            funcionario.setEspecialidade(body.getEspecialidade());
        }
        if (body.getTelefone() != null) {
            funcionario.setTelefone(body.getTelefone());
        }
        if (body.getSenha() != null) {
            String criptografada = passwordEncoder.encode(body.getSenha());
            funcionario.setSenha(criptografada);
        }
        if (body.getEmail() != null) {
            funcionario.setEmail(body.getEmail());
        }
        return funcionario;
    }
}
