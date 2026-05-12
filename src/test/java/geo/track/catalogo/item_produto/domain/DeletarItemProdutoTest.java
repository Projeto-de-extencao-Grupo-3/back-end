package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
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
class DeletarItemProdutoTest {

    @Mock
    private ItemProdutoService ITEM_PRODUTO_SERVICE;

    @Mock
    private ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;

    @Mock
    private Log log;

    private DeletarItemProduto useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeletarItemProduto(ITEM_PRODUTO_SERVICE, ITEM_PRODUTO_REPOSITORY, log);
    }

    @Test
    void deveExecutarComSucesso() {
        Integer id = 1;
        ItemProduto item = new ItemProduto();
        item.setIdRegistroPeca(id);

        when(ITEM_PRODUTO_SERVICE.buscarRegistroPorId(id)).thenReturn(item);

        assertDoesNotThrow(() -> useCase.execute(id));

        verify(ITEM_PRODUTO_SERVICE).buscarRegistroPorId(id);
        verify(ITEM_PRODUTO_REPOSITORY).delete(item);
    }

    @Test
    void devePropagateDataNotFoundException_QuandoItemNaoEncontrado() {
        Integer id = 999;

        when(ITEM_PRODUTO_SERVICE.buscarRegistroPorId(id))
                .thenThrow(new DataNotFoundException("Item não encontrado", Domains.ITEM_PRODUTO));

        assertThrows(DataNotFoundException.class, () -> useCase.execute(id));
        verify(ITEM_PRODUTO_REPOSITORY, never()).delete(any());
    }
}
