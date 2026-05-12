package geo.track.catalogo.item_servico.domain;

import geo.track.catalogo.item_servico.infraestructure.persistence.ItemServicoRepository;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ItemServico;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        Integer id = 1;
        ItemServico item = new ItemServico();
        item.setIdRegistroServico(id);

        when(ITEM_SERVICO_SERVICE.buscarItemServicoPorId(id)).thenReturn(item);

        assertDoesNotThrow(() -> useCase.execute(id));

        verify(ITEM_SERVICO_SERVICE).buscarItemServicoPorId(id);
        verify(ITEM_SERVICO_REPOSITORY).delete(item);
    }

    @Test
    void devePropagateDataNotFoundException_QuandoItemNaoEncontrado() {
        Integer id = 999;

        when(ITEM_SERVICO_SERVICE.buscarItemServicoPorId(id))
                .thenThrow(new DataNotFoundException("Item de Serviço não encontrado", Domains.ITEM_SERVICO));

        assertThrows(DataNotFoundException.class, () -> useCase.execute(id));
        verify(ITEM_SERVICO_REPOSITORY, never()).delete(any());
    }
}
