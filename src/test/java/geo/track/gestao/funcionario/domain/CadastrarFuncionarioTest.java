package geo.track.gestao.funcionario.domain;

import geo.track.gestao.funcionario.infraestructure.persistence.FuncionarioRepository;
import geo.track.gestao.oficina.domain.OficinaService;
import geo.track.gestao.oficina.infraestructure.persistence.OficinaRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    }

    @Test
    void deveLancarConflictException_QuandoFuncionarioRepositoryExistsbyemailBodyGetemail() {
    }

}
