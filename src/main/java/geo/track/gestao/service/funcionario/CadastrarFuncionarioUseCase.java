package geo.track.gestao.service.funcionario;

import geo.track.gestao.entity.Funcionario;
import geo.track.dto.funcionarios.request.RequestPostFuncionario;

public interface CadastrarFuncionarioUseCase {
    Funcionario execute(RequestPostFuncionario request);
}

