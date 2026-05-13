package geo.track.gestao.funcionario.domain;

import geo.track.gestao.funcionario.infraestructure.FuncionarioMapper;
import geo.track.gestao.funcionario.infraestructure.persistence.FuncionarioRepository;
import geo.track.gestao.funcionario.infraestructure.persistence.entity.Funcionario;
import geo.track.gestao.funcionario.infraestructure.request.RequestPutFuncionario;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        RequestPutFuncionario body = new RequestPutFuncionario();
        body.setId(1);
        body.setNome("Novo Nome");

        Funcionario existente = new Funcionario();
        existente.setIdFuncionario(1);

        Funcionario atualizado = new Funcionario();
        atualizado.setIdFuncionario(1);

        when(FUNCIONARIO_REPOSITORY.existsById(1)).thenReturn(true);
        when(FUNCIONARIO_REPOSITORY.getByIdFuncionario(1)).thenReturn(existente);
        when(mapper.toEntityUpdate(existente, body)).thenReturn(atualizado);
        when(FUNCIONARIO_REPOSITORY.save(atualizado)).thenReturn(atualizado);

        Funcionario retorno = useCase.execute(body);

        assertSame(atualizado, retorno);
        verify(FUNCIONARIO_REPOSITORY).save(atualizado);
    }

    @Test
    void deveLancarDataNotFoundException_QuandoFuncionarioRepositoryExistsbyidBodyGetid() {
        RequestPutFuncionario body = new RequestPutFuncionario();
        body.setId(1);

        when(FUNCIONARIO_REPOSITORY.existsById(1)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> useCase.execute(body));
    }

}
