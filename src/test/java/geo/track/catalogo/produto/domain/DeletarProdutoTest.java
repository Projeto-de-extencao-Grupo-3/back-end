package geo.track.catalogo.produto.domain;

import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.catalogo.produto.infraestructure.persistence.entity.Produto;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.domain.OrdemDeServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarProdutoTest {

    @Mock
    private ProdutoRepository PRODUTO_REPOSITORY;

    @Mock
    private ProdutoService PRODUTO_SERVICE;

    @Mock
    private OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @Mock
    private Log log;

    private DeletarProduto useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeletarProduto(PRODUTO_REPOSITORY, PRODUTO_SERVICE, ORDEM_SERVICO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
        Integer id = 1;
        Produto produto = new Produto();
        produto.setIdProduto(id);
        produto.setAtivo(true);

        when(PRODUTO_SERVICE.buscarProdutosPorId(id)).thenReturn(produto);

        useCase.execute(id);

        assertFalse(produto.getAtivo());
        verify(PRODUTO_SERVICE).buscarProdutosPorId(id);
        verify(PRODUTO_REPOSITORY).save(produto);
    }

    @Test
    void devePropagateDataNotFoundException_QuandoProdutoNaoEncontrado() {
        Integer id = 999;

        when(PRODUTO_SERVICE.buscarProdutosPorId(id))
                .thenThrow(new DataNotFoundException("Produto não encontrado", Domains.PRODUTO));

        assertThrows(DataNotFoundException.class, () -> useCase.execute(id));
        verify(PRODUTO_REPOSITORY, never()).save(any());
    }
}
