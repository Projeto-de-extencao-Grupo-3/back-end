package geo.track.jornada.domain.controle;

import geo.track.infraestructure.log.Log;
import geo.track.jornada.domain.OrdemDeServicoService;
import geo.track.jornada.domain.controle.DefinirDataSaidaPrevista;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefinirDataSaidaPrevistaTest {

    @Mock
    private OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @Mock
    private OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Mock
    private Log log;

    private DefinirDataSaidaPrevista useCase;

    @BeforeEach
    void setUp() {
        useCase = new DefinirDataSaidaPrevista(ORDEM_SERVICO_SERVICE, ORDEM_SERVICO_REPOSITORY, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}