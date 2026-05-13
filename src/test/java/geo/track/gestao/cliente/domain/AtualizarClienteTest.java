package geo.track.gestao.cliente.domain;

import geo.track.gestao.cliente.infraestructure.persistence.ClienteRepository;
import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.request.cliente.RequestPutCliente;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        RequestPutCliente body = new RequestPutCliente();
        body.setIdCliente(1);
        body.setNome("Novo Nome");

        Cliente existente = new Cliente();
        existente.setIdCliente(1);
        existente.setNome("Antigo");

        when(CLIENTE_SERVICE.buscarClientePorId(1)).thenReturn(existente);
        when(CLIENTE_REPOSITORY.save(existente)).thenReturn(existente);

        Cliente retorno = useCase.execute(body);

        assertSame(existente, retorno);
        verify(CLIENTE_SERVICE).buscarClientePorId(1);
        verify(CLIENTE_REPOSITORY).save(existente);
    }

}
