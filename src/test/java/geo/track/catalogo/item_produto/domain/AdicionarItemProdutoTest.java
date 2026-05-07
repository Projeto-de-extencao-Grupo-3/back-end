package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.catalogo.produto.domain.ProdutoService;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.domain.OrdemDeServicoService;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdicionarItemProdutoTest {

    @Mock
    private ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;

    @Mock
    private ProdutoService PRODUTO_SERVICE;

    @Mock
    private OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @Mock
    private OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Mock
    private Log log;

    private AdicionarItemProduto useCase;

    @BeforeEach
    void setUp() {
        useCase = new AdicionarItemProduto(ITEM_PRODUTO_REPOSITORY, PRODUTO_SERVICE, ORDEM_SERVICO_SERVICE, ORDEM_SERVICO_REPOSITORY, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}
