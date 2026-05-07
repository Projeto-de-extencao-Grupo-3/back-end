package geo.track.catalogo.item_servico.domain;

import geo.track.catalogo.item_servico.domain.DeletarItemServico;
import geo.track.catalogo.item_servico.infraestructure.persistence.ItemServicoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeletarItemServicoTest {

    @Mock
    private ItemServicoService ITEM_SERVICO_SERVICE;

    @Mock
    private ItemServicoRepository ITEM_SERVICO_REPOSITORY;

    @Mock
    private Log log;

    private DeletarItemServico useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeletarItemServico(ITEM_SERVICO_SERVICE, ITEM_SERVICO_REPOSITORY, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}