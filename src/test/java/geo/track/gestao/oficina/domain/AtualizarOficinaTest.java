package geo.track.gestao.oficina.domain;

import geo.track.gestao.oficina.infraestructure.persistence.OficinaRepository;
import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.gestao.oficina.infraestructure.request.RequestPutOficina;
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
class AtualizarOficinaTest {

    @Mock
    private OficinaRepository OFICINA_REPOSITORY;

    @Mock
    private OficinaService OFICINA_SERVICE;

    @Mock
    private Log log;

    private AtualizarOficina useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarOficina(OFICINA_REPOSITORY, OFICINA_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
        RequestPutOficina body = new RequestPutOficina();
        body.setIdOficina(1);
        body.setEmail("novo@email.com");
        body.setStatus(false);

        Oficina existente = new Oficina();
        existente.setIdOficina(1);

        Oficina salva = new Oficina();
        salva.setIdOficina(1);

        when(OFICINA_REPOSITORY.existsById(1)).thenReturn(true);
        when(OFICINA_SERVICE.buscarOficinaPorId(1)).thenReturn(existente);
        when(OFICINA_REPOSITORY.save(existente)).thenReturn(salva);

        Oficina retorno = useCase.execute(body);

        assertSame(salva, retorno);
        verify(OFICINA_REPOSITORY).save(existente);
    }

    @Test
    void deveLancarDataNotFoundException_QuandoOficinaRepositoryExistsbyidBodyGetidoficina() {
        RequestPutOficina body = new RequestPutOficina();
        body.setIdOficina(1);

        when(OFICINA_REPOSITORY.existsById(1)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> useCase.execute(body));
    }

}
