package geo.track.catalogo.produto.domain;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.Servico;
import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.catalogo.produto.infraestructure.persistence.entity.Produto;
import geo.track.catalogo.produto.infraestructure.request.ProdutoRequest;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarProdutoTest {

    @Mock
    private ProdutoRepository PRODUTO_REPOSITORY;

    @Mock
    private Log log;

    private CadastrarProduto useCase;

    @BeforeEach
    void setUp() {
        useCase = new CadastrarProduto(PRODUTO_REPOSITORY, log);
    }

    private ProdutoRequest buildRequest() {
        ProdutoRequest request = new ProdutoRequest();
        request.setNome("Filtro de óleo");
        request.setFornecedorNf("NF-001");
        request.setPrecoCompra(50.0);
        request.setPrecoVenda(100.0);
        request.setQuantidadeEstoque(10);
        request.setVisivelOrcamento(true);
        request.setTipoServico(Servico.MECANICA);
        return request;
    }

    @Test
    void deveExecutarComSucesso() {
        ProdutoRequest request = buildRequest();

        Produto produtoSalvo = new Produto();
        produtoSalvo.setIdProduto(1);
        produtoSalvo.setAtivo(true);
        produtoSalvo.setNome(request.getNome());

        when(PRODUTO_REPOSITORY.save(any(Produto.class))).thenReturn(produtoSalvo);

        Produto resultado = useCase.execute(request);

        assertNotNull(resultado);
        assertTrue(resultado.getAtivo());
        assertEquals("Filtro de óleo", resultado.getNome());
        verify(PRODUTO_REPOSITORY).save(any(Produto.class));
    }
}
