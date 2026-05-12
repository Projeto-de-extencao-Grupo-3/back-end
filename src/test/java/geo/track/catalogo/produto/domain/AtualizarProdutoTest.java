package geo.track.catalogo.produto.domain;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.Servico;
import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.catalogo.produto.infraestructure.persistence.entity.Produto;
import geo.track.catalogo.produto.infraestructure.request.ProdutoRequest;
import geo.track.infraestructure.exception.DataNotFoundException;
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
class AtualizarProdutoTest {

    @Mock
    private ProdutoRepository PRODUTO_REPOSITORY;

    @Mock
    private Log log;

    private AtualizarProduto useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarProduto(PRODUTO_REPOSITORY, log);
    }

    private ProdutoRequest buildRequest() {
        ProdutoRequest request = new ProdutoRequest();
        request.setNome("Filtro de ar");
        request.setFornecedorNf("NF-002");
        request.setPrecoCompra(30.0);
        request.setPrecoVenda(60.0);
        request.setQuantidadeEstoque(5);
        request.setVisivelOrcamento(true);
        request.setTipoServico(Servico.MECANICA);
        return request;
    }

    @Test
    void deveExecutarComSucesso() {
        Integer id = 1;
        ProdutoRequest request = buildRequest();

        Produto produtoSalvo = new Produto();
        produtoSalvo.setIdProduto(id);
        produtoSalvo.setAtivo(true);
        produtoSalvo.setNome(request.getNome());

        when(PRODUTO_REPOSITORY.existsByIdProdutoAndAtivoTrue(id)).thenReturn(true);
        when(PRODUTO_REPOSITORY.save(any(Produto.class))).thenReturn(produtoSalvo);

        Produto resultado = useCase.execute(id, request);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdProduto());
        assertTrue(resultado.getAtivo());
        verify(PRODUTO_REPOSITORY).save(any(Produto.class));
    }

    @Test
    void deveLancarDataNotFoundException_QuandoProdutoNaoEncontrado() {
        Integer id = 999;
        ProdutoRequest request = buildRequest();

        when(PRODUTO_REPOSITORY.existsByIdProdutoAndAtivoTrue(id)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> useCase.execute(id, request));
        verify(PRODUTO_REPOSITORY, never()).save(any());
    }
}
