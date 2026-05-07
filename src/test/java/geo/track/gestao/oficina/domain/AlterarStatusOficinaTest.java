package geo.track.gestao.oficina.domain;

import geo.track.gestao.oficina.infraestructure.persistence.OficinaRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlterarStatusOficinaTest {

    @Mock
    private OficinaRepository OFICINA_REPOSITORY;

    @Mock
    private OficinaService OFICINA_SERVICEA;

    @Mock
    private Log log;

    private AlterarStatusOficina useCase;

    @BeforeEach
    void setUp() {
        useCase = new AlterarStatusOficina(OFICINA_REPOSITORY, OFICINA_SERVICEA, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}
