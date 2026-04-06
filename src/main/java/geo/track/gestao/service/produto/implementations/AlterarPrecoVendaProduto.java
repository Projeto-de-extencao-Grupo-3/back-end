package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.Produto;
import geo.track.gestao.entity.repository.ProdutoRepository;
import geo.track.gestao.service.ProdutoService;
import geo.track.gestao.service.produto.AlterarPrecoVendaProdutoUseCase;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlterarPrecoVendaProduto implements AlterarPrecoVendaProdutoUseCase {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final ProdutoService PRODUTO_SERVICE;
    private final Log log;

    public Produto execute(RequestPatchPrecoVenda body) {
        Produto prod = PRODUTO_SERVICE.buscarProdutosPorId(body.getId());
        prod.setPrecoVenda(body.getPrecoVenda());

        log.info("Preco de venda atualizado para o produto ID: {}", prod.getIdProduto());
        return PRODUTO_REPOSITORY.save(prod);
    }
}

