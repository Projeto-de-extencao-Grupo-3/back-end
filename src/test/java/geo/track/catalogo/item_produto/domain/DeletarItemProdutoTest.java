package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }

}
