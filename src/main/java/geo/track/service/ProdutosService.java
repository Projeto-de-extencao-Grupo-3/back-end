package geo.track.service;

import geo.track.domain.Produtos;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.ProdutosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutosService {
    private final ProdutosRepository repository;

    public Produtos cadastrar(Produtos prod){
        return repository.save(prod);
    }

    public List<Produtos> listar(){
        return repository.findAll();
    }

    public Produtos findProdutoById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Não existe um produto com esse ID", "Produtos")
        );
    }

    public Produtos putProdutos(Integer id, Produtos produtoAtt) {
        Optional<Produtos> produtoOpt = repository.findById(id);

        if(produtoOpt.isEmpty()){
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        }

        Produtos prod = produtoOpt.get();

        return repository.save(prod);
    }

    public Produtos patchQtdEstoque(RequestPatchQtdEstoque produtoAtt){
        Optional<Produtos> produtoOpt = repository.findById(produtoAtt.getId());

        if(produtoOpt.isEmpty()){
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        }

        Produtos prod = produtoOpt.get();
        prod.setQuantidadeEstoque(prod.getQuantidadeEstoque());
        return repository.save(prod);
    }

    public Produtos patchPrecoCompra(RequestPatchPrecoCompra produtoAtt){
        Optional<Produtos> produtoOpt = repository.findById(produtoAtt.getId());

        if(produtoOpt.isEmpty()){
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        }

        Produtos prod = produtoOpt.get();
        prod.setPrecoCompra(prod.getPrecoCompra());
        return repository.save(prod);
    }

    public Produtos patchPrecoVenda(RequestPatchPrecoVenda produtoAtt){
        Optional<Produtos> produtoOpt = repository.findById(produtoAtt.getId());

        if(produtoOpt.isEmpty()){
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        }

        Produtos prod = produtoOpt.get();
        prod.setPrecoVenda(prod.getPrecoVenda());
        return repository.save(prod);
    }

    public void excluir(Integer id){

        if(!repository.existsById(id)){
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        }

        repository.deleteById(id);
    }




}
