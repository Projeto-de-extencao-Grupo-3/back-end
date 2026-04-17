package geo.track.catalogo.produto.domain;

import geo.track.catalogo.produto.domain.entity.Produto;
import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import geo.track.catalogo.produto.application.CadastrarProdutoUseCase;
import geo.track.catalogo.produto.infraestructure.ProdutoMapper;
import geo.track.catalogo.produto.infraestructure.request.ProdutoRequest;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarProduto implements CadastrarProdutoUseCase {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final Log log;

    public Produto execute(ProdutoRequest body) {
        log.info("Cadastrando novo produto: {}", body.getNome());
        Produto produto = ProdutoMapper.toEntity(body);
        produto.setAtivo(true);
        return PRODUTO_REPOSITORY.save(produto);
    }
}

