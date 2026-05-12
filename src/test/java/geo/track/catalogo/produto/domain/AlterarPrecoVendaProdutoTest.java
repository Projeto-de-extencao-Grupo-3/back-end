package geo.track.catalogo.produto.domain;

import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.catalogo.produto.infraestructure.persistence.entity.Produto;
import geo.track.catalogo.produto.infraestructure.request.RequestPatchPrecoVenda;
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
class AlterarPrecoVendaProdutoTest {

    @Mock
    private ProdutoRepository PRODUTO_REPOSITORY;

    @Mock
    private ProdutoService PRODUTO_SERVICE;

    @Mock
    private Log log;

    private AlterarPrecoVendaProduto useCase;

    @BeforeEach
    void setUp() {
        useCase = new AlterarPrecoVendaProduto(PRODUTO_REPOSITORY, PRODUTO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
        Integer id = 1;
        Double novoPreco = 150.0;
        RequestPatchPrecoVenda request = new RequestPatchPrecoVenda(id, novoPreco);

        Produto produto = new Produto();
        produto.setIdProduto(id);
        produto.setPrecoVenda(100.0);

        Produto produtoSalvo = new Produto();
        produtoSalvo.setIdProduto(id);
        produtoSalvo.setPrecoVenda(novoPreco);

        when(PRODUTO_SERVICE.buscarProdutosPorId(id)).thenReturn(produto);
        when(PRODUTO_REPOSITORY.save(produto)).thenReturn(produtoSalvo);

        Produto resultado = useCase.execute(request);

        assertNotNull(resultado);
        assertEquals(novoPreco, resultado.getPrecoVenda());
        verify(PRODUTO_SERVICE).buscarProdutosPorId(id);
        verify(PRODUTO_REPOSITORY).save(produto);
    }

    @Test
    void devePropagateDataNotFoundException_QuandoProdutoNaoEncontrado() {
        Integer id = 999;
        RequestPatchPrecoVenda request = new RequestPatchPrecoVenda(id, 200.0);

        when(PRODUTO_SERVICE.buscarProdutosPorId(id))
                .thenThrow(new DataNotFoundException("Produto não encontrado", Domains.PRODUTO));

        assertThrows(DataNotFoundException.class, () -> useCase.execute(request));
        verify(PRODUTO_REPOSITORY, never()).save(any());
    }
}
