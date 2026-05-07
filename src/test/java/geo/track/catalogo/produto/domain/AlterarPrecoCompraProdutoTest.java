package geo.track.catalogo.produto.domain;

import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlterarPrecoCompraProdutoTest {

    @Mock
    private ProdutoRepository PRODUTO_REPOSITORY;

    @Mock
    private ProdutoService PRODUTO_SERVICE;

    @Mock
    private Log log;

    private AlterarPrecoCompraProduto useCase;

    @BeforeEach
    void setUp() {
        useCase = new AlterarPrecoCompraProduto(PRODUTO_REPOSITORY, PRODUTO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}
