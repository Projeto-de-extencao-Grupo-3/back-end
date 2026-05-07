package geo.track.gestao.oficina.domain;

import geo.track.gestao.oficina.infraestructure.persistence.OficinaRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CadastrarOficinaTest {

    @Mock
    private OficinaRepository OFICINA_REPOSITORY;

    @Mock
    private Log log;

    private CadastrarOficina useCase;

    @BeforeEach
    void setUp() {
        useCase = new CadastrarOficina(OFICINA_REPOSITORY, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

    @Test
    void deveLancarConflictException_QuandoRegraDeNegocioViolada() {
    }

}
