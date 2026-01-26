package geo.track.service;

import geo.track.domain.ItemProduto;
import geo.track.domain.OrdemDeServico;
import geo.track.domain.Produto;
import geo.track.dto.itensProdutos.RequestPostItemProduto;
import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.exception.BadRequestException;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.mapper.ItensProdutosMapper;
import geo.track.repository.ItemProdutoRepository;
import geo.track.repository.OrdemDeServicoRepository;
import geo.track.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemProdutoService {
    private final ItemProdutoRepository ITENS_PRODUTO_REPOSITORY;
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    public ItemProduto cadastrarRegistro(RequestPostItemProduto body) {
        OrdemDeServico ordemServico = ORDEM_SERVICO_REPOSITORY.findById(body.fkOrdemServico()).orElseThrow(() -> new DataNotFoundException("Ordem de Serviço não encontrada!", "Ordem de Serviço"));
        Produto produto = PRODUTO_REPOSITORY.findById(body.fkProduto()).orElseThrow(() -> new DataNotFoundException("Produto não encontrado!", "Produtos"));

        ItemProduto registroProduto = ItensProdutosMapper.toEntity(null, body.quantidade(), body.precoProduto(), false, produto, ordemServico);

        return ITENS_PRODUTO_REPOSITORY.save(registroProduto);
    }

    public List<ItemProduto> listarRegistros() {
        return ITENS_PRODUTO_REPOSITORY.findAll();
    }

    public ItemProduto buscarRegistroPorID(Integer id) {
        ItemProduto registroProduto = ITENS_PRODUTO_REPOSITORY.findById(id).orElseThrow(() -> new DataNotFoundException("Registro de Produto não encontrado!", "ItensProdutos"));
        return registroProduto;
    }

    public List<ItemProduto> listarPelaOrdemServico(OrdemDeServico ordemDeServicos) {
        List<ItemProduto> registroProdutos = ITENS_PRODUTO_REPOSITORY.findAllByFkOrdemServicoIdOrdemServico(ordemDeServicos.getIdOrdemServico());
        return registroProdutos;
    }

    public ItemProduto atualizarRegistro(Integer id, RequestPutItemProduto body) {
        ItemProduto registroDesejado = ITENS_PRODUTO_REPOSITORY.findById(id).orElseThrow(() -> new DataNotFoundException("Registro de Produto não encontrado", "ItensProdutos"));

        if (body.baixado() != null && body.baixado().equals(false)) {
            throw new BadRequestException("Não é possível retirar a baixa do sistema!", "ItensProdutos");
        }

        // Pra quem não entender isso daqui abstrai aqueles IF's feios dms e tira a responsabilidade da service de mapear
        ItemProduto registroAtualizado = ItensProdutosMapper.updateEntity(registroDesejado, body);

        if (body.baixado() != null && body.baixado().equals(true) && registroDesejado.getBaixado().equals(false)) {
            Produto estoqueProduto = registroAtualizado.getFkPeca();

            if (estoqueProduto.getQuantidadeEstoque() - body.quantidade() < 0) {
                throw new ConflictException("Não é possível diminuir esta quantidade do estoque!", "ItensProduto");
            }

            estoqueProduto.setQuantidadeEstoque(estoqueProduto.getQuantidadeEstoque() - registroAtualizado.getQuantidade());

            // atualiza valor do estoque
            PRODUTO_REPOSITORY.save(estoqueProduto);
        }

        return ITENS_PRODUTO_REPOSITORY.save(registroAtualizado);
    }

    public void deletarRegistro(Integer id) {
        ITENS_PRODUTO_REPOSITORY.deleteById(id);
    }
}
