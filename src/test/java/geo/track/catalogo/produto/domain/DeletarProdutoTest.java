package geo.track.catalogo.produto.domain;

import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.domain.OrdemDeServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }

    @Test
    void deveLancarRuntimeException_QuandoItemDeProdutoIdDNaoPodeSer() {
    }

}
