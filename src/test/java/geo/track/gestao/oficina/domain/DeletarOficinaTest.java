package geo.track.gestao.oficina.domain;

import geo.track.gestao.oficina.infraestructure.persistence.OficinaRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeletarOficinaTest {

    @Mock
    private OficinaRepository OFICINA_REPOSITORY;

    @Mock
    private Log log;

    private DeletarOficina useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeletarOficina(OFICINA_REPOSITORY, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

    @Test
    void deveLancarDataNotFoundException_QuandoRegraDeNegocioViolada() {
    }

}
