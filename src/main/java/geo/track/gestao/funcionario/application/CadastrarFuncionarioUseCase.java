package geo.track.gestao.funcionario.application;

import geo.track.gestao.funcionario.infraestructure.persistence.entity.Funcionario;
import geo.track.gestao.funcionario.infraestructure.request.RequestPostFuncionario;

public interface CadastrarFuncionarioUseCase {
    Funcionario execute(RequestPostFuncionario request);
}

