package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
import geo.track.catalogo.item_produto.infraestructure.request.RequestPutItemProduto;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        Integer id = 1;
        RequestPutItemProduto request = new RequestPutItemProduto(5, 45.0, false);

        ItemProduto itemExistente = new ItemProduto();
        itemExistente.setIdRegistroPeca(id);
        itemExistente.setQuantidade(2);
        itemExistente.setPrecoPeca(30.0);
        itemExistente.setBaixado(false);

        ItemProduto itemAtualizado = new ItemProduto();
        itemAtualizado.setIdRegistroPeca(id);
        itemAtualizado.setQuantidade(5);
        itemAtualizado.setPrecoPeca(45.0);
        itemAtualizado.setBaixado(false);

        when(ITEM_PRODUTO_SERVICE.buscarRegistroPorId(id)).thenReturn(itemExistente);
        when(ITEM_PRODUTO_REPOSITORY.save(any(ItemProduto.class))).thenReturn(itemAtualizado);

        ItemProduto resultado = useCase.execute(id, request);

        assertNotNull(resultado);
        assertEquals(5, resultado.getQuantidade());
        assertEquals(45.0, resultado.getPrecoPeca());
        verify(ITEM_PRODUTO_SERVICE).buscarRegistroPorId(id);
        verify(ITEM_PRODUTO_REPOSITORY).save(any(ItemProduto.class));
    }

    @Test
    void devePropagateDataNotFoundException_QuandoItemNaoEncontrado() {
        Integer id = 999;
        RequestPutItemProduto request = new RequestPutItemProduto(5, 45.0, false);

        when(ITEM_PRODUTO_SERVICE.buscarRegistroPorId(id))
                .thenThrow(new DataNotFoundException("Item não encontrado", Domains.ITEM_PRODUTO));

        assertThrows(DataNotFoundException.class, () -> useCase.execute(id, request));
        verify(ITEM_PRODUTO_REPOSITORY, never()).save(any());
    }
}
