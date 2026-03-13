package geo.track.service;

import geo.track.domain.ItemProduto;
import geo.track.domain.OrdemDeServico;
import geo.track.domain.Produto;
import geo.track.dto.itensProdutos.RequestPostItemProduto;
import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.exception.BadRequestException;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.ItemProdutoExceptionMessages;
import geo.track.exception.constraint.message.OrdemDeServicoExceptionMessages;
import geo.track.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.log.Log;
import geo.track.mapper.ItemProdutoMapper;
import geo.track.repository.ItemProdutoRepository;
import geo.track.repository.OrdemDeServicoRepository;
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

        ItemProduto registroProduto = ItemProdutoMapper.toEntity(null, body.quantidade(), body.precoProduto(), false, produto, ordemServico);

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

        if (body.baixado() != null && body.baixado().equals(false)) {
            log.warn("Tentativa inválida de retirar a baixa do item ID: {}", id);
            throw new BadRequestException("Não é possível retirar a baixa do sistema!", Domains.ITEM_SERVICO);
        }

        log.info("Mapeando alterações para o item ID: {}", id);
        ItemProduto registroAtualizado = ItemProdutoMapper.updateEntity(registroDesejado, body);

        if (body.baixado() != null && body.baixado().equals(true) && registroDesejado.getBaixado().equals(false)) {
            Produto estoqueProduto = registroAtualizado.getFkPeca();

            if (estoqueProduto.getQuantidadeEstoque() - body.quantidade() < 0) {
                throw new ConflictException("Não é possível diminuir esta quantidade do estoque!", Domains.ITEM_SERVICO);
            }

            estoqueProduto.setQuantidadeEstoque(estoqueProduto.getQuantidadeEstoque() - registroAtualizado.getQuantidade());

            log.info("Realizando baixa no estoque do produto ID: {}. Nova quantidade: {}", estoqueProduto.getIdProduto(), estoqueProduto.getQuantidadeEstoque());
            PRODUTO_REPOSITORY.save(estoqueProduto);
        }

        ItemProduto atualizado = ITEM_PRODUTO_REPOSITORY.save(registroAtualizado);
        log.info("Item de produto ID: {} atualizado com sucesso.", id);
        return atualizado;
    }

    public void deletarRegistro(Integer id) {
        log.info("Deletando item de produto ID: {}", id);
        ITEM_PRODUTO_REPOSITORY.deleteById(id);
        log.info("Item de produto ID: {} deletado com sucesso.", id);
    }
}
