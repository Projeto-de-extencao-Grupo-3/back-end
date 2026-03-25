package geo.track.gestao.service.funcionario;

import geo.track.gestao.entity.Funcionario;
import geo.track.dto.funcionarios.request.RequestPutFuncionario;

public interface AtualizarFuncionarioUseCase {
    Funcionario execute(RequestPutFuncionario request);
}

