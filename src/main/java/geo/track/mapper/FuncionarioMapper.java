package geo.track.mapper;

import geo.track.domain.Funcionarios;
import geo.track.dto.funcionarios.FuncionarioResponse;

import java.util.List;
import java.util.stream.Collectors;

public class FuncionarioMapper {
    public static FuncionarioResponse toResponse(Funcionarios entity) {
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

    public static List<FuncionarioResponse> toResponse(List<Funcionarios> entities) {
        return entities.stream()
                .map(FuncionarioMapper::toResponse)
                .collect(Collectors.toList());
    }
}
