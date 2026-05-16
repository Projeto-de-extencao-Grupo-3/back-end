package geo.track.gestao.funcionario.domain;

import geo.track.gestao.funcionario.infraestructure.persistence.FuncionarioRepository;
import geo.track.gestao.funcionario.infraestructure.persistence.entity.Funcionario;
import geo.track.gestao.funcionario.infraestructure.request.RequestPostFuncionario;
import geo.track.gestao.oficina.domain.OficinaService;
import geo.track.gestao.oficina.infraestructure.persistence.OficinaRepository;
import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarFuncionarioTest {

    @Mock
    private FuncionarioRepository FUNCIONARIO_REPOSITORY;

    @Mock
    private OficinaRepository OFICINA_REPOSITORY;

    @Mock
    private OficinaService OFICINA_SERVICE;

    @Mock
    private PasswordEncoder PASSWORD_ENCODER;

    @Mock
    private Log log;

    private CadastrarFuncionario useCase;

    @BeforeEach
    void setUp() {
        useCase = new CadastrarFuncionario(FUNCIONARIO_REPOSITORY, OFICINA_REPOSITORY, OFICINA_SERVICE, PASSWORD_ENCODER, log);
    }

    @Test
    void deveExecutarComSucesso() {
        RequestPostFuncionario body = new RequestPostFuncionario();
        body.setNome("José");
        body.setCargo("Mecânico");
        body.setEspecialidade("Motor");
        body.setTelefone("11999990000");
        body.setSenha("123456");
        body.setEmail("func@email.com");
        body.setFkOficina(1);

        Oficina oficina = new Oficina();
        oficina.setIdOficina(1);

        Funcionario salvo = new Funcionario();
        salvo.setIdFuncionario(10);

        when(FUNCIONARIO_REPOSITORY.existsByEmail(body.getEmail())).thenReturn(false);
        when(OFICINA_SERVICE.buscarOficinaPorId(1)).thenReturn(oficina);
        when(PASSWORD_ENCODER.encode("123456")).thenReturn("senha-cripto");
        when(FUNCIONARIO_REPOSITORY.save(any(Funcionario.class))).thenReturn(salvo);

        Funcionario retorno = useCase.execute(body);

        assertSame(salvo, retorno);
        verify(FUNCIONARIO_REPOSITORY).save(any(Funcionario.class));
    }

    @Test
    void deveLancarConflictException_QuandoFuncionarioRepositoryExistsbyemailBodyGetemail() {
        RequestPostFuncionario body = new RequestPostFuncionario();
        body.setEmail("func@email.com");

        when(FUNCIONARIO_REPOSITORY.existsByEmail(body.getEmail())).thenReturn(true);

        assertThrows(ConflictException.class, () -> useCase.execute(body));

        verify(FUNCIONARIO_REPOSITORY, never()).save(any());
    }

}
