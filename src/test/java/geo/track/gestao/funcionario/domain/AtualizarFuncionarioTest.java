package geo.track.gestao.funcionario.domain;

import geo.track.gestao.funcionario.domain.AtualizarFuncionario;
import geo.track.gestao.funcionario.infraestructure.FuncionarioMapper;
import geo.track.gestao.funcionario.infraestructure.persistence.FuncionarioRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AtualizarFuncionarioTest {

    @Mock
    private FuncionarioRepository FUNCIONARIO_REPOSITORY;

    @Mock
    private FuncionarioMapper mapper;

    @Mock
    private Log log;

    private AtualizarFuncionario useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarFuncionario(FUNCIONARIO_REPOSITORY, mapper, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

    @Test
    void deveLancarDataNotFoundException_QuandoRegraDeNegocioViolada() {
    }

}