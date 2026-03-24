package geo.track.service;

import geo.track.infraestructure.annotation.ToRefactor;
import geo.track.gestao.entity.Produto;
import geo.track.dto.produtos.ProdutoRequest;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.gestao.enums.Servico;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.util.ProdutoMapper;
import geo.track.gestao.entity.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final Log log;

    @ToRefactor
    public Produto cadastrar(ProdutoRequest body){
        log.info("Cadastrando novo produto: {}", body.getNome());
        Produto produto = ProdutoMapper.toEntity(body);
        produto.setAtivo(true);
        return PRODUTO_REPOSITORY.save(produto);
    }

    public List<Produto> listar(){
        log.info("Listando todos os produtos");
        return PRODUTO_REPOSITORY.findByAtivoTrue();
    }

    public HashMap<String, List<Produto>> listarProdutosPorStatus(){
        log.info("Listando todos os produtos por status");
        HashMap<String, List<Produto>> response = new HashMap<>();

        List<Servico> tipoServicos = Arrays.stream(Servico.values()).toList();

        tipoServicos.forEach(t -> {
            response.put(t.name(), PRODUTO_REPOSITORY.findByTipoServico(t));
        });

        return response;
    }

    public Produto findProdutoById(Integer id) {
        log.info("Buscando produto com ID: {}", id);
        return PRODUTO_REPOSITORY.findByIdProdutoAndAtivoTrue(id).orElseThrow(
                () -> new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, Domains.PRODUTO)
        );
    }

    @ToRefactor
    public Produto putProdutos(Integer id, ProdutoRequest body) {
        if(PRODUTO_REPOSITORY.existsByIdProdutoAndAtivoTrue(id)){
            log.info("Atualizando produto (PUT) ID: {}", id);
            Produto produto = ProdutoMapper.toEntity(body);
            produto.setIdProduto(id);
            produto.setAtivo(true);
            return PRODUTO_REPOSITORY.save(produto);
        }

        log.error("Falha ao atualizar produto: ID {} não encontrado", id);
        throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, Domains.PRODUTO);
    }

    @ToRefactor
    public Produto patchQtdEstoque(RequestPatchQtdEstoque body){
        Optional<Produto> produtoOpt = PRODUTO_REPOSITORY.findByIdProdutoAndAtivoTrue(body.getId());

        if(produtoOpt.isEmpty()){
            log.error("Falha ao atualizar estoque: Produto ID {} não encontrado", body.getId());
            throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, Domains.PRODUTO);
        }

        Produto prod = produtoOpt.get();
        prod.setQuantidadeEstoque(body.getQuantidadeEstoque());
        log.info("Quantidade em estoque atualizada para o produto ID: {}. Nova quantidade: {}", prod.getIdProduto(), prod.getQuantidadeEstoque());
        return PRODUTO_REPOSITORY.save(prod);
    }

    @ToRefactor
    public Produto patchPrecoCompra(RequestPatchPrecoCompra body){
        Optional<Produto> produtoOpt = PRODUTO_REPOSITORY.findByIdProdutoAndAtivoTrue(body.getId());

        if(produtoOpt.isEmpty()){
            log.error("Falha ao atualizar preço de compra: Produto ID {} não encontrado", body.getId());
            throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, Domains.PRODUTO);
        }

        Produto prod = produtoOpt.get();
        prod.setPrecoCompra(body.getPrecoCompra());
        log.info("Preço de compra atualizado para o produto ID: {}", prod.getIdProduto());
        return PRODUTO_REPOSITORY.save(prod);
    }

    @ToRefactor
    public Produto patchPrecoVenda(RequestPatchPrecoVenda body){
        Optional<Produto> produtoOpt = PRODUTO_REPOSITORY.findByIdProdutoAndAtivoTrue(body.getId());

        if(produtoOpt.isEmpty()){
            log.error("Falha ao atualizar preço de venda: Produto ID {} não encontrado", body.getId());
            throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, Domains.PRODUTO);
        }

        Produto prod = produtoOpt.get();
        prod.setPrecoVenda(body.getPrecoVenda());
        log.info("Preço de venda atualizado para o produto ID: {}", prod.getIdProduto());
        return PRODUTO_REPOSITORY.save(prod);
    }

    @ToRefactor
    public void excluir(Integer id){
        if(!PRODUTO_REPOSITORY.existsByIdProdutoAndAtivoTrue(id)){
            log.error("Falha ao excluir: Produto ID {} não encontrado", id);
            throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, Domains.PRODUTO);
        }
        Produto produto = PRODUTO_REPOSITORY.findById(id).get();

        log.info("Excluindo produto ID: {}", id);
        produto.setAtivo(false);
        PRODUTO_REPOSITORY.save(produto);
    }
}
