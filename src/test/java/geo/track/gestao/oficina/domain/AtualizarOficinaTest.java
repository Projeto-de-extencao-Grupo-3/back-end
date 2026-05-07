package geo.track.gestao.oficina.domain;

import geo.track.gestao.oficina.infraestructure.persistence.OficinaRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }

    @Test
    void deveLancarDataNotFoundException_QuandoOficinaRepositoryExistsbyidBodyGetidoficina() {
    }

}
