package geo.track.gestao.cliente.domain.contato;

import geo.track.gestao.cliente.domain.ClienteService;
import geo.track.gestao.cliente.infraestructure.persistence.ContatoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CadastrarContatoTest {

    @Mock
    private ContatoRepository CONTATO_REPOSITORY;

    @Mock
    private ContatoService CONTATO_SERVICE;

    @Mock
    private ClienteService CLIENTE_SERVICE;

    @Mock
    private Log log;

    private CadastrarContato useCase;

    @BeforeEach
    void setUp() {
        useCase = new CadastrarContato(CONTATO_REPOSITORY, CONTATO_SERVICE, CLIENTE_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

    @Test
    void deveLancarConflictException_QuandoContatoServiceExisteemailporclienteIdclienteBodyGetemail() {
    }

    @Test
    void deveLancarConflictException_QuandoContatoServiceExistetelefoneporclienteIdclienteBodyGettelefone() {
    }

}
