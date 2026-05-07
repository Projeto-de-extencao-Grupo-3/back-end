package geo.track.gestao.cliente.domain;

import geo.track.gestao.cliente.infraestructure.persistence.ClienteRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AtualizarClienteTest {

    @Mock
    private ClienteRepository CLIENTE_REPOSITORY;

    @Mock
    private ClienteService CLIENTE_SERVICE;

    @Mock
    private Log log;

    private AtualizarCliente useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarCliente(CLIENTE_REPOSITORY, CLIENTE_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}
