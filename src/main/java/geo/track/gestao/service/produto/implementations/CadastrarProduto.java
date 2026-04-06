package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.Produto;
import geo.track.gestao.entity.repository.ProdutoRepository;
import geo.track.gestao.service.produto.CadastrarProdutoUseCase;
import geo.track.gestao.util.ProdutoMapper;
import geo.track.dto.produtos.ProdutoRequest;
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

