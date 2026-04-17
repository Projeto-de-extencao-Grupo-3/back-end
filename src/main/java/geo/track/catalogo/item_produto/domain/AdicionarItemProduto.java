package geo.track.catalogo.item_produto.domain;

import geo.track.catalogo.item_produto.infraestructure.persistence.entity.ItemProduto;
import geo.track.catalogo.produto.domain.ProdutoService;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.catalogo.produto.domain.entity.Produto;
import geo.track.catalogo.item_produto.infraestructure.persistence.ItemProdutoRepository;
import geo.track.catalogo.item_produto.application.AdicionarItemProdutoUseCase;
import geo.track.catalogo.item_produto.infraestructure.ItemProdutoMapper;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.catalogo.item_produto.infraestructure.request.RequestPostItemProduto;
import geo.track.jornada.domain.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdicionarItemProduto implements AdicionarItemProdutoUseCase {
    private final ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;
    private final ProdutoService PRODUTO_SERVICE;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;
    private final Log log;

    public ItemProduto execute(Integer id, RequestPostItemProduto body) {
        log.info("Iniciando cadastro de novo item de produto para a Ordem de Servico ID: {}", id);
        OrdemDeServico ordemServico = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(id);
        Produto produto = PRODUTO_SERVICE.buscarProdutosPorId(body.fkProduto());

        ItemProduto registroProduto = ItemProdutoMapper.toEntity(body, produto, ordemServico);

        ItemProduto salvo = ITEM_PRODUTO_REPOSITORY.save(registroProduto);
        log.info("Item de produto cadastrado com sucesso. ID Gerado: {}", salvo.getIdRegistroPeca());
        return salvo;
    }
}

