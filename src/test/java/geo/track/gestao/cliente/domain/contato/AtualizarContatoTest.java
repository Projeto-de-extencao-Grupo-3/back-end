package geo.track.gestao.cliente.domain.contato;

import geo.track.gestao.cliente.infraestructure.persistence.ContatoRepository;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Contato;
import geo.track.gestao.cliente.infraestructure.request.contato.RequestPutContato;
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
        RequestPutContato body = new RequestPutContato(1, "11999990000", "novo@email.com", "Nome", "Dep");

        Contato existente = new Contato();
        existente.setIdContato(2);

        when(CONTATO_SERVICE.existeEmailPorClienteExcluindoContato(1, 2, body.getEmail())).thenReturn(false);
        when(CONTATO_SERVICE.existeTelefonePorClienteExcluindoContato(1, 2, body.getTelefone())).thenReturn(false);
        when(CONTATO_SERVICE.buscarContatoPorId(1, 2)).thenReturn(existente);
        when(CONTATO_REPOSITORY.save(existente)).thenReturn(existente);

        Contato retorno = useCase.execute(1, 2, body);

        assertSame(existente, retorno);
        verify(CONTATO_REPOSITORY).save(existente);
    }

    @Test
    void deveLancarConflictException_QuandoContatoServiceExisteemailporclienteexcluindocontatoIdclienteIdcontatoBodyGetemail() {
        RequestPutContato body = new RequestPutContato(1, "11999990000", "novo@email.com", "Nome", "Dep");
        when(CONTATO_SERVICE.existeEmailPorClienteExcluindoContato(1, 2, body.getEmail())).thenReturn(true);

        assertThrows(ConflictException.class, () -> useCase.execute(1, 2, body));

        verify(CONTATO_REPOSITORY, never()).save(any());
    }

    @Test
    void deveLancarConflictException_QuandoContatoServiceExistetelefoneporclienteexcluindocontatoIdclienteIdcontatoBodyGettelefone() {
        RequestPutContato body = new RequestPutContato(1, "11999990000", "novo@email.com", "Nome", "Dep");
        when(CONTATO_SERVICE.existeEmailPorClienteExcluindoContato(1, 2, body.getEmail())).thenReturn(false);
        when(CONTATO_SERVICE.existeTelefonePorClienteExcluindoContato(1, 2, body.getTelefone())).thenReturn(true);

        assertThrows(ConflictException.class, () -> useCase.execute(1, 2, body));

        verify(CONTATO_REPOSITORY, never()).save(any());
    }

}
