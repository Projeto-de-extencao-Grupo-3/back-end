package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
import geo.track.catalogo.item_produto.infraestructure.request.RequestPostItemProduto;
import geo.track.catalogo.produto.domain.ProdutoService;
import geo.track.catalogo.produto.infraestructure.persistence.entity.Produto;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.domain.OrdemDeServicoService;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        Integer idOrdemServico = 1;
        Integer idProduto = 10;
        RequestPostItemProduto request = new RequestPostItemProduto(idProduto, 3, 29.90);

        OrdemDeServico ordemDeServico = new OrdemDeServico();
        ordemDeServico.setIdOrdemServico(idOrdemServico);

        Produto produto = new Produto();
        produto.setIdProduto(idProduto);
        produto.setNome("Vela de ignição");

        ItemProduto itemSalvo = new ItemProduto();
        itemSalvo.setIdRegistroPeca(100);
        itemSalvo.setQuantidade(3);
        itemSalvo.setPrecoPeca(29.90);
        itemSalvo.setBaixado(false);
        itemSalvo.setFkProduto(produto);
        itemSalvo.setFkOrdemServico(ordemDeServico);

        when(ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdemServico)).thenReturn(ordemDeServico);
        when(PRODUTO_SERVICE.buscarProdutosPorId(idProduto)).thenReturn(produto);
        when(ITEM_PRODUTO_REPOSITORY.save(any(ItemProduto.class))).thenReturn(itemSalvo);

        ItemProduto resultado = useCase.execute(idOrdemServico, request);

        assertNotNull(resultado);
        assertEquals(100, resultado.getIdRegistroPeca());
        assertEquals(3, resultado.getQuantidade());
        assertFalse(resultado.getBaixado());
        verify(ORDEM_SERVICO_SERVICE).buscarOrdemServicoPorId(idOrdemServico);
        verify(PRODUTO_SERVICE).buscarProdutosPorId(idProduto);
        verify(ITEM_PRODUTO_REPOSITORY).save(any(ItemProduto.class));
    }
}
