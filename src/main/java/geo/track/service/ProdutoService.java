package geo.track.service;

import geo.track.domain.Produto;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.exception.DataNotFoundException;
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
                () -> new DataNotFoundException("Não existe um produto com esse ID", "Produtos")
        );
    }

    public Produto putProdutos(Integer id, Produto produtoAtt) {
        if(PRODUTO_REPOSITORY.existsById(id)){
            produtoAtt.setIdProduto(id);
            Produto prod = PRODUTO_REPOSITORY.save(produtoAtt);
            return prod;
        }

        throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
    }

    public Produto patchQtdEstoque(RequestPatchQtdEstoque produtoAtt){
        Optional<Produto> produtoOpt = PRODUTO_REPOSITORY.findById(produtoAtt.getId());

        if(produtoOpt.isEmpty()){
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        }

        Produto prod = produtoOpt.get();
        prod.setQuantidadeEstoque(prod.getQuantidadeEstoque());
        return PRODUTO_REPOSITORY.save(prod);
    }

    public Produto patchPrecoCompra(RequestPatchPrecoCompra produtoAtt){
        Optional<Produto> produtoOpt = PRODUTO_REPOSITORY.findById(produtoAtt.getId());

        if(produtoOpt.isEmpty()){
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        }

        Produto prod = produtoOpt.get();
        prod.setPrecoCompra(prod.getPrecoCompra());
        return PRODUTO_REPOSITORY.save(prod);
    }

    public Produto patchPrecoVenda(RequestPatchPrecoVenda produtoAtt){
        Optional<Produto> produtoOpt = PRODUTO_REPOSITORY.findById(produtoAtt.getId());

        if(produtoOpt.isEmpty()){
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        }

        Produto prod = produtoOpt.get();
        prod.setPrecoVenda(prod.getPrecoVenda());
        return PRODUTO_REPOSITORY.save(prod);
    }

    public void excluir(Integer id){
        if(!PRODUTO_REPOSITORY.existsById(id)){
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        }

        PRODUTO_REPOSITORY.deleteById(id);
    }




}
