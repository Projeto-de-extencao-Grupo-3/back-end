package geo.track.catalogo.item_servico.domain;

import geo.track.catalogo.item_servico.domain.AtualizarItemServico;
import geo.track.catalogo.item_servico.infraestructure.persistence.ItemServicoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AtualizarItemServicoTest {

    @Mock
    private ItemServicoRepository ITEM_SERVICO_REPOSITORY;

    @Mock
    private ItemServicoService ITEM_SERVICO_SERVICE;

    @Mock
    private Log log;

    private AtualizarItemServico useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarItemServico(ITEM_SERVICO_REPOSITORY, ITEM_SERVICO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}