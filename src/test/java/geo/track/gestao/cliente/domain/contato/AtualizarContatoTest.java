package geo.track.gestao.cliente.domain.contato;

import geo.track.gestao.cliente.domain.contato.AtualizarContato;
import geo.track.gestao.cliente.infraestructure.persistence.ContatoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AtualizarContatoTest {

    @Mock
    private ContatoRepository CONTATO_REPOSITORY;

    @Mock
    private ContatoService CONTATO_SERVICE;

    @Mock
    private Log log;

    private AtualizarContato useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarContato(CONTATO_REPOSITORY, CONTATO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

    @Test
    void deveLancarConflictException_QuandoRegraDeNegocioViolada() {
    }

    @Test
    void deveLancarConflictException_QuandoRegraDeNegocioViolada2() {
    }

}