package geo.track.service;

import geo.track.domain.Produto;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.EnumDomains;
import geo.track.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository PRODUTO_REPOSITORY;

    public Produto cadastrar(Produto prod){
        return PRODUTO_REPOSITORY.save(prod);
    }

    public List<Produto> listar(){
        return PRODUTO_REPOSITORY.findAll();
    }

    public Produto findProdutoById(Integer id) {
        return PRODUTO_REPOSITORY.findById(id).orElseThrow(
                () -> new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, EnumDomains.PRODUTO)
        );
    }

    public Produto putProdutos(Integer id, Produto produtoAtt) {
        if(PRODUTO_REPOSITORY.existsById(id)){
            produtoAtt.setIdProduto(id);
            Produto prod = PRODUTO_REPOSITORY.save(produtoAtt);
            return prod;
        }

        throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, EnumDomains.PRODUTO);
    }

    public Produto patchQtdEstoque(RequestPatchQtdEstoque produtoAtt){
        Optional<Produto> produtoOpt = PRODUTO_REPOSITORY.findById(produtoAtt.getId());

        if(produtoOpt.isEmpty()){
            throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, EnumDomains.PRODUTO);
        }

        Produto prod = produtoOpt.get();
        prod.setQuantidadeEstoque(produtoAtt.getQuantidadeEstoque());
        return PRODUTO_REPOSITORY.save(prod);
    }

    public Produto patchPrecoCompra(RequestPatchPrecoCompra produtoAtt){
        Optional<Produto> produtoOpt = PRODUTO_REPOSITORY.findById(produtoAtt.getId());

        if(produtoOpt.isEmpty()){
            throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, EnumDomains.PRODUTO);
        }

        Produto prod = produtoOpt.get();
        prod.setPrecoCompra(produtoAtt.getPrecoCompra());
        return PRODUTO_REPOSITORY.save(prod);
    }

    public Produto patchPrecoVenda(RequestPatchPrecoVenda produtoAtt){
        Optional<Produto> produtoOpt = PRODUTO_REPOSITORY.findById(produtoAtt.getId());

        if(produtoOpt.isEmpty()){
            throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, EnumDomains.PRODUTO);
        }

        Produto prod = produtoOpt.get();
        prod.setPrecoVenda(produtoAtt.getPrecoVenda());
        return PRODUTO_REPOSITORY.save(prod);
    }

    public void excluir(Integer id){
        if(!PRODUTO_REPOSITORY.existsById(id)){
            throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, EnumDomains.PRODUTO);
        }

        PRODUTO_REPOSITORY.deleteById(id);
    }
}
