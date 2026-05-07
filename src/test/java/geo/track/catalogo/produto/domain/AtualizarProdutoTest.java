package geo.track.catalogo.produto.domain;

import geo.track.catalogo.produto.domain.AtualizarProduto;
import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void deveExecutarComSucesso() {
    }

    @Test
    void deveLancarDataNotFoundException_QuandoRegraDeNegocioViolada() {
    }

}