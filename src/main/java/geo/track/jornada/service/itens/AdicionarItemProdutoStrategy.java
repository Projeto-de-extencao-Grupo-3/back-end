package geo.track.jornada.service.itens;

import geo.track.gestao.entity.ItemProduto;
import geo.track.gestao.entity.Produto;
import geo.track.jornada.request.itens.RequestPostItemProduto;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.mapper.ItemProdutoMapper;
import geo.track.gestao.entity.repository.ItemProdutoRepository;
import geo.track.service.OrdemDeServicoService;
import geo.track.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ADICIONAR SERVICO AO ORÇAMENTO **/
@Component
@RequiredArgsConstructor
public class AdicionarItemProdutoStrategy implements ItensJornadaStrategy {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;
    private final ProdutoService PRODUTO_SERVICE;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.ADICIONAR_ITEM_PRODUTO.equals(tipoJornada);
    }

    @Override
    public ItemProduto execute(Integer idOrdemServico, GetJornada getRequest) {
        RequestPostItemProduto request = (RequestPostItemProduto) getRequest;
        Produto produto = PRODUTO_SERVICE.findProdutoById(request.fkProduto());
        OrdemDeServico ordemDeServico = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdemServico);

        ItemProduto itemProduto = ItemProdutoMapper.toEntity(request, produto, ordemDeServico);

        return ITEM_PRODUTO_REPOSITORY.save(itemProduto);
    }
}
