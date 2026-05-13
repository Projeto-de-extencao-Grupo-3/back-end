package geo.track.gestao.funcionario.domain;

import geo.track.gestao.funcionario.infraestructure.persistence.FuncionarioRepository;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(FUNCIONARIO_REPOSITORY.existsById(1)).thenReturn(true);

        useCase.execute(1);

        verify(FUNCIONARIO_REPOSITORY).deleteById(1);
    }

    @Test
    void deveLancarDataNotFoundException_QuandoFuncionarioRepositoryExistsbyidId() {
        when(FUNCIONARIO_REPOSITORY.existsById(1)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> useCase.execute(1));
    }

}
