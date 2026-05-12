package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
import geo.track.catalogo.produto.domain.ProdutoService;
import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.catalogo.produto.infraestructure.persistence.entity.Produto;
import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.infraestructure.exception.BadRequestException;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    private Produto buildProduto(Integer estoque) {
        Produto produto = new Produto();
        produto.setIdProduto(1);
        produto.setQuantidadeEstoque(estoque);
        return produto;
    }

    private ItemProduto buildItem(Produto produto, Integer quantidade, boolean baixado) {
        ItemProduto item = new ItemProduto();
        item.setIdRegistroPeca(10);
        item.setFkProduto(produto);
        item.setQuantidade(quantidade);
        item.setBaixado(baixado);
        return item;
    }

    /**
     * tela=1, quantidade explícita igual à do item → baixa completa:
     * apenas setBaixado(true), sem alterar quantidade.
     */
    @Test
    void deveRealizarBaixaCompleta_QuandoTela1EQuantidadeIgualAoItem() {
        Produto produto = buildProduto(10);
        ItemProduto item = buildItem(produto, 4, false);

        when(ITEM_PRODUTO_SERVICE.buscarRegistroPorId(10)).thenReturn(item);

        Boolean resultado = useCase.execute(10, 4, 1);

        assertTrue(resultado);
        assertTrue(item.getBaixado());
        assertEquals(6, produto.getQuantidadeEstoque());
        assertEquals(4, item.getQuantidade());
        verify(ITEM_PRODUTO_REPOSITORY).save(item);
        verify(PRODUTO_REPOSITORY).save(produto);
    }

    /**
     * tela=1, quantidade explícita diferente da quantidade registrada no item → baixa parcial:
     * setBaixado(true) e atualiza quantidade do item para o valor passado.
     */
    @Test
    void deveRealizarBaixaParcial_QuandoTela1EQuantidadeDiferenteDaDoItem() {
        Produto produto = buildProduto(10);
        ItemProduto item = buildItem(produto, 5, false);

        when(ITEM_PRODUTO_SERVICE.buscarRegistroPorId(10)).thenReturn(item);

        Boolean resultado = useCase.execute(10, 3, 1);

        assertTrue(resultado);
        assertTrue(item.getBaixado());
        assertEquals(3, item.getQuantidade());
        assertEquals(7, produto.getQuantidadeEstoque());
        verify(ITEM_PRODUTO_REPOSITORY).save(item);
        verify(PRODUTO_REPOSITORY).save(produto);
    }

    /**
     * tela=1, quantidade null → usa a quantidade do próprio item (baixa completa implícita).
     */
    @Test
    void deveRealizarBaixaComQuantidadeDoItem_QuandoTela1EQuantidadeNula() {
        Produto produto = buildProduto(10);
        ItemProduto item = buildItem(produto, 4, false);

        when(ITEM_PRODUTO_SERVICE.buscarRegistroPorId(10)).thenReturn(item);

        Boolean resultado = useCase.execute(10, null, 1);

        assertTrue(resultado);
        assertTrue(item.getBaixado());
        assertEquals(6, produto.getQuantidadeEstoque());
        verify(ITEM_PRODUTO_REPOSITORY).save(item);
        verify(PRODUTO_REPOSITORY).save(produto);
    }

    /**
     * tela=2 → repõe estoque (adiciona quantidade ao produto sem alterar ItemProduto).
     */
    @Test
    void deveAdicionarEstoque_QuandoTela2() {
        Produto produto = buildProduto(5);

        when(PRODUTO_SERVICE.buscarProdutosPorId(1)).thenReturn(produto);

        Boolean resultado = useCase.execute(1, 10, 2);

        assertTrue(resultado);
        assertEquals(15, produto.getQuantidadeEstoque());
        verify(PRODUTO_REPOSITORY).save(produto);
        verifyNoInteractions(ITEM_PRODUTO_SERVICE, ITEM_PRODUTO_REPOSITORY);
    }

    /**
     * Cenário: quantidade efetiva <= 0 → lança BadRequestException antes de qualquer operação.
     */
    @Test
    void deveLancarBadRequestException_QuandoQuantidadeInvalida() {
        Produto produto = buildProduto(10);
        ItemProduto item = buildItem(produto, 0, false);

        when(ITEM_PRODUTO_SERVICE.buscarRegistroPorId(10)).thenReturn(item);

        // tela=1, quantidade null e item com quantidade 0 → quantidadeEfetiva = 0 → inválida
        assertThrows(BadRequestException.class, () -> useCase.execute(10, null, 1));
        verify(ITEM_PRODUTO_REPOSITORY, never()).save(any());
        verify(PRODUTO_REPOSITORY, never()).save(any());
    }

    /**
     * Cenário: item já foi baixado → lança BadRequestException.
     */
    @Test
    void deveLancarBadRequestException_QuandoItemJaBaixado() {
        Produto produto = buildProduto(10);
        ItemProduto item = buildItem(produto, 3, true); // já baixado

        when(ITEM_PRODUTO_SERVICE.buscarRegistroPorId(10)).thenReturn(item);

        assertThrows(BadRequestException.class, () -> useCase.execute(10, 3, 1));
        verify(ITEM_PRODUTO_REPOSITORY, never()).save(any());
        verify(PRODUTO_REPOSITORY, never()).save(any());
    }

    /**
     * Cenário: estoque insuficiente para a quantidade solicitada → lança BadBusinessRuleException.
     */
    @Test
    void deveLancarBadBusinessRuleException_QuandoEstoqueInsuficiente() {
        Produto produto = buildProduto(2);
        ItemProduto item = buildItem(produto, 5, false);

        when(ITEM_PRODUTO_SERVICE.buscarRegistroPorId(10)).thenReturn(item);

        assertThrows(BadBusinessRuleException.class, () -> useCase.execute(10, 5, 1));
        verify(ITEM_PRODUTO_REPOSITORY, never()).save(any());
        verify(PRODUTO_REPOSITORY, never()).save(any());
    }
}
