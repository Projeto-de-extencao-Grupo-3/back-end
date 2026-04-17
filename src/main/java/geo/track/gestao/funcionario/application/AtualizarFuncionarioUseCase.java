package geo.track.gestao.funcionario.application;

import geo.track.gestao.funcionario.infraestructure.persistence.entity.Funcionario;
import geo.track.gestao.funcionario.infraestructure.request.RequestPutFuncionario;

public interface AtualizarFuncionarioUseCase {
    Funcionario execute(RequestPutFuncionario request);
}

