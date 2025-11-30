package geo.track.service;

import geo.track.domain.ItensProdutos;
import geo.track.domain.OrdemDeServicos;
import geo.track.domain.Produtos;
import geo.track.dto.itensProdutos.RequestPostItemProduto;
import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.exception.BadRequestException;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.mapper.ItensProdutosMapper;
import geo.track.repository.ItensProdutosRepository;
import geo.track.repository.OrdemDeServicosRepository;
import geo.track.repository.ProdutosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItensProdutosService {
    private final ItensProdutosRepository ITENS_PRODUTO_REPOSITORY;
    private final ProdutosRepository PRODUTO_REPOSITORY;
    private final OrdemDeServicosRepository ORDEM_SERVICO_REPOSITORY;

    public ItensProdutos cadastrarRegistro(RequestPostItemProduto body) {
        OrdemDeServicos ordemServico = ORDEM_SERVICO_REPOSITORY.findById(body.fkOrdemServico()).orElseThrow(() -> new DataNotFoundException("Ordem de Serviço não encontrada!", "Ordem de Serviço"));
        Produtos produto = PRODUTO_REPOSITORY.findById(body.fkProduto()).orElseThrow(() -> new DataNotFoundException("Produto não encontrado!", "Produtos"));

        ItensProdutos registroProduto = ItensProdutosMapper.toEntity(null, body.quantidade(), body.precoProduto(), false, produto, ordemServico);

        return ITENS_PRODUTO_REPOSITORY.save(registroProduto);
    }

    public List<ItensProdutos> listarRegistros() {
        return ITENS_PRODUTO_REPOSITORY.findAll();
    }

    public ItensProdutos buscarRegistroPorID(Integer id) {
        ItensProdutos registroProduto = ITENS_PRODUTO_REPOSITORY.findById(id).orElseThrow(() -> new DataNotFoundException("Registro de Produto não encontrado!", "ItensProdutos"));
        return registroProduto;
    }

    public List<ItensProdutos> listarPelaOrdemServico(OrdemDeServicos ordemDeServicos) {
        List<ItensProdutos> registroProdutos = ITENS_PRODUTO_REPOSITORY.findAllByFkOrdemServicoIdOrdemServico(ordemDeServicos.getIdOrdemServico());
        return registroProdutos;
    }

    public ItensProdutos atualizarRegistro(Integer id, RequestPutItemProduto body) {
        ItensProdutos registroDesejado = ITENS_PRODUTO_REPOSITORY.findById(id).orElseThrow(() -> new DataNotFoundException("Registro de Produto não encontrado", "ItensProdutos"));

        if (body.baixado() != null && body.baixado().equals(false)) {
            throw new BadRequestException("Não é possível retirar a baixa do sistema!", "ItensProdutos");
        }

        // Pra quem não entender isso daqui abstrai aqueles IF's feios dms e tira a responsabilidade da service de mapear
        ItensProdutos registroAtualizado = ItensProdutosMapper.updateEntity(registroDesejado, body);

        if (body.baixado() != null && body.baixado().equals(true) && registroDesejado.getBaixado().equals(false)) {
            Produtos estoqueProduto = registroAtualizado.getFkPeca();

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
