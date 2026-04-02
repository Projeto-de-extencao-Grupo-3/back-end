package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.ItemProduto;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.gestao.entity.Produto;
import geo.track.gestao.entity.repository.ItemProdutoRepository;
import geo.track.gestao.entity.repository.ProdutoRepository;
import geo.track.gestao.service.produto.AdicionarItemProdutoUseCase;
import geo.track.gestao.util.ItemProdutoMapper;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.OrdemDeServicoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.request.itens.RequestPostItemProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdicionarItemProdutoImplementation implements AdicionarItemProdutoUseCase {
    private final ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;
    private final Log log;

    public ItemProduto execute(Integer id, RequestPostItemProduto body) {
        log.info("Iniciando cadastro de novo item de produto para a Ordem de Servico ID: {}", id);
        OrdemDeServico ordemServico = ORDEM_SERVICO_REPOSITORY.findById(id).orElseThrow(() -> new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO));
        Produto produto = PRODUTO_REPOSITORY.findById(body.fkProduto()).orElseThrow(() -> new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, Domains.PRODUTO));

        ItemProduto registroProduto = ItemProdutoMapper.toEntity(body, produto, ordemServico);

        ItemProduto salvo = ITEM_PRODUTO_REPOSITORY.save(registroProduto);
        log.info("Item de produto cadastrado com sucesso. ID Gerado: {}", salvo.getIdRegistroPeca());
        return salvo;
    }
}

