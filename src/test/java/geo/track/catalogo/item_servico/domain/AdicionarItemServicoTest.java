package geo.track.catalogo.item_servico.domain;

import geo.track.catalogo.item_servico.infraestructure.persistence.ItemServicoRepository;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.domain.OrdemDeServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdicionarItemServicoTest {

    @Mock
    private ItemServicoRepository ITEM_SERVICO_REPOSITORY;

    @Mock
    private OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @Mock
    private Log log;

    private AdicionarItemServico useCase;

    @BeforeEach
    void setUp() {
        useCase = new AdicionarItemServico(ITEM_SERVICO_REPOSITORY, ORDEM_SERVICO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}
