package geo.track.gestao.funcionario.domain;

import geo.track.gestao.funcionario.domain.DeletarFuncionario;
import geo.track.gestao.funcionario.infraestructure.persistence.FuncionarioRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeletarFuncionarioTest {

    @Mock
    private FuncionarioRepository FUNCIONARIO_REPOSITORY;

    @Mock
    private Log log;

    private DeletarFuncionario useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeletarFuncionario(FUNCIONARIO_REPOSITORY, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

    @Test
    void deveLancarDataNotFoundException_QuandoRegraDeNegocioViolada() {
    }

}