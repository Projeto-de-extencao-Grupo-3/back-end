package geo.track.gestao.cliente.domain.contato;

import geo.track.gestao.cliente.domain.ClienteService;
import geo.track.gestao.cliente.infraestructure.persistence.ContatoRepository;
import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Contato;
import geo.track.gestao.cliente.infraestructure.request.contato.RequestPostContato;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        RequestPostContato body = new RequestPostContato("11999990000", "a@a.com", "João", "Comercial");
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);

        Contato salvo = new Contato();
        salvo.setIdContato(10);

        when(CONTATO_SERVICE.existeEmailPorCliente(1, body.getEmail())).thenReturn(false);
        when(CONTATO_SERVICE.existeTelefonePorCliente(1, body.getTelefone())).thenReturn(false);
        when(CLIENTE_SERVICE.buscarClientePorId(1)).thenReturn(cliente);
        when(CONTATO_REPOSITORY.save(any(Contato.class))).thenReturn(salvo);

        Contato retorno = useCase.execute(1, body);

        assertSame(salvo, retorno);
        verify(CONTATO_REPOSITORY).save(any(Contato.class));
    }

    @Test
    void deveLancarConflictException_QuandoContatoServiceExisteemailporclienteIdclienteBodyGetemail() {
        RequestPostContato body = new RequestPostContato("11999990000", "a@a.com", "João", "Comercial");
        when(CONTATO_SERVICE.existeEmailPorCliente(1, body.getEmail())).thenReturn(true);

        assertThrows(ConflictException.class, () -> useCase.execute(1, body));

        verify(CONTATO_REPOSITORY, never()).save(any());
    }

    @Test
    void deveLancarConflictException_QuandoContatoServiceExistetelefoneporclienteIdclienteBodyGettelefone() {
        RequestPostContato body = new RequestPostContato("11999990000", "a@a.com", "João", "Comercial");
        when(CONTATO_SERVICE.existeEmailPorCliente(1, body.getEmail())).thenReturn(false);
        when(CONTATO_SERVICE.existeTelefonePorCliente(1, body.getTelefone())).thenReturn(true);

        assertThrows(ConflictException.class, () -> useCase.execute(1, body));

        verify(CONTATO_REPOSITORY, never()).save(any());
    }

}
