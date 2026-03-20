package geo.track.service;

import geo.track.domain.ItemProduto;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.domain.Produto;
import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.exception.BadBusinessRuleException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.ItemProdutoExceptionMessages;
import geo.track.exception.constraint.message.OrdemDeServicoExceptionMessages;
import geo.track.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.request.itens.RequestPostItemProduto;
import geo.track.log.Log;
import geo.track.mapper.ItemProdutoMapper;
import geo.track.repository.ItemProdutoRepository;
import geo.track.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemProdutoService {
    private final ItemProdutoRepository ITEM_PRODUTO_REPOSITORY;
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;
    private final Log log;

    public ItemProduto cadastrarRegistro(RequestPostItemProduto body) {
        log.info("Iniciando cadastro de novo item de produto para a Ordem de Serviço ID: {}", body.fkOrdemServico());
        OrdemDeServico ordemServico = ORDEM_SERVICO_REPOSITORY.findById(body.fkOrdemServico()).orElseThrow(() -> new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO));
        Produto produto = PRODUTO_REPOSITORY.findById(body.fkProduto()).orElseThrow(() -> new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, Domains.PRODUTO));

        ItemProduto registroProduto = ItemProdutoMapper.toEntity(body, produto, ordemServico);

        ItemProduto salvo = ITEM_PRODUTO_REPOSITORY.save(registroProduto);
        log.info("Item de produto cadastrado com sucesso. ID Gerado: {}", salvo.getIdRegistroPeca());
        return salvo;
    }

    public List<ItemProduto> listarRegistros() {
        log.info("Listando todos os registros de itens de produtos.");
        return ITEM_PRODUTO_REPOSITORY.findAll();
    }

    public ItemProduto buscarRegistroPorID(Integer id) {
        log.info("Buscando item de produto pelo ID: {}", id);
        ItemProduto registroProduto = ITEM_PRODUTO_REPOSITORY.findById(id).orElseThrow(() -> new DataNotFoundException(ItemProdutoExceptionMessages.ITEM_PRODUTO_NAO_ENCONTRADO, Domains.ITEM_SERVICO));
        return registroProduto;
    }

    public List<ItemProduto> listarPelaOrdemServico(OrdemDeServico body) {
        log.info("Listando itens de produtos vinculados à Ordem de Serviço ID: {}", body.getIdOrdemServico());
        List<ItemProduto> registroProdutos = ITEM_PRODUTO_REPOSITORY.findAllByFkOrdemServicoIdOrdemServico(body.getIdOrdemServico());
        return registroProdutos;
    }

    public ItemProduto atualizarRegistro(Integer id, RequestPutItemProduto body) {
        log.info("Iniciando atualização do item de produto ID: {}", id);
        ItemProduto registroDesejado = ITEM_PRODUTO_REPOSITORY.findById(id).orElseThrow(() -> new DataNotFoundException(ItemProdutoExceptionMessages.ITEM_PRODUTO_NAO_ENCONTRADO, Domains.ITEM_SERVICO));

        log.info("Mapeando alterações para o item ID: {}", id);
        ItemProduto registroAtualizado = ItemProdutoMapper.updateEntity(registroDesejado, body);

        ItemProduto atualizado = ITEM_PRODUTO_REPOSITORY.save(registroAtualizado);
        log.info("Item de produto ID: {} atualizado com sucesso.", id);
        return atualizado;
    }

    public void deletarRegistro(Integer id) {
        log.info("Deletando item de produto ID: {}", id);
        ITEM_PRODUTO_REPOSITORY.deleteById(id);
        log.info("Item de produto ID: {} deletado com sucesso.", id);
    }

    public Boolean realizarBaixaEstoque(Integer id) {
        log.info("Iniciando processo de baixa de estoque para o item de produto ID: {}", id);
        ItemProduto itemProduto = this.buscarRegistroPorID(id);

        if (itemProduto.getBaixado().equals(true)) {
            log.warn("Falha ao realizar baixa: Item ID {} já possui baixa realizada.", id);
            throw new BadBusinessRuleException(ItemProdutoExceptionMessages.BAIXA_JA_REALIZADA, Domains.ITEM_SERVICO);
        } else if (!itemProduto.possivelRealizarBaixaNoEstoque()) {
            log.warn("Falha ao realizar baixa: Estoque insuficiente para o produto ID {}. Requerido: {}, Disponível: {}",
                itemProduto.getFkProduto().getIdProduto(), itemProduto.getQuantidade(), itemProduto.getFkProduto().getQuantidadeEstoque());
            throw new BadBusinessRuleException(ItemProdutoExceptionMessages.QUANTIDADE_INSUFICIENTE, Domains.ITEM_SERVICO);
        }

        Produto estoqueProduto = itemProduto.getFkProduto();
        log.info("Reduzindo {} unidades do produto ID: {}", itemProduto.getQuantidade(), estoqueProduto.getIdProduto());
        estoqueProduto.setQuantidadeEstoque(estoqueProduto.getQuantidadeEstoque() - itemProduto.getQuantidade());
        itemProduto.setBaixado(true);

        ITEM_PRODUTO_REPOSITORY.save(itemProduto);
        PRODUTO_REPOSITORY.save(estoqueProduto);
        log.info("Baixa de estoque realizada com sucesso para o item ID: {}", id);
        return true;
    }
}
