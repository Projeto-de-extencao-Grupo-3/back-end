package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.domain.RealizarBaixaEstoqueItemProduto;
import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.catalogo.produto.domain.ProdutoService;
import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RealizarBaixaEstoqueItemProdutoTest {

    @Mock
    private ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;

    @Mock
    private ProdutoRepository PRODUTO_REPOSITORY;

    @Mock
    private ItemProdutoService ITEM_PRODUTO_SERVICE;

    @Mock
    private ProdutoService PRODUTO_SERVICE;

    @Mock
    private Log log;

    private RealizarBaixaEstoqueItemProduto useCase;

    @BeforeEach
    void setUp() {
        useCase = new RealizarBaixaEstoqueItemProduto(ITEM_PRODUTO_REPOSITORY, PRODUTO_REPOSITORY, ITEM_PRODUTO_SERVICE, PRODUTO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

    @Test
    void deveLancarBadRequestException_QuandoQuantidadeInvalida() {
    }

    @Test
    void deveLancarBadRequestException_QuandoItemJaBaixado() {
    }

    @Test
    void deveLancarBadBusinessRuleException_QuandoEstoqueInsuficiente() {
    }

}