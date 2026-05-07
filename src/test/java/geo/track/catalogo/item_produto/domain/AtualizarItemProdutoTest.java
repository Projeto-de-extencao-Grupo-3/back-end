package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.domain.AtualizarItemProduto;
import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AtualizarItemProdutoTest {

    @Mock
    private ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;

    @Mock
    private ItemProdutoService ITEM_PRODUTO_SERVICE;

    @Mock
    private Log log;

    private AtualizarItemProduto useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarItemProduto(ITEM_PRODUTO_REPOSITORY, ITEM_PRODUTO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}