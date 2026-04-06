package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.Produto;
import geo.track.gestao.entity.repository.ProdutoRepository;
import geo.track.gestao.service.produto.AtualizarProdutoUseCase;
import geo.track.gestao.util.ProdutoMapper;
import geo.track.dto.produtos.ProdutoRequest;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtualizarProduto implements AtualizarProdutoUseCase {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final Log log;

    public Produto execute(Integer id, ProdutoRequest body) {
        if (PRODUTO_REPOSITORY.existsByIdProdutoAndAtivoTrue(id)) {
            log.info("Atualizando produto (PUT) ID: {}", id);
            Produto produto = ProdutoMapper.toEntity(body);
            produto.setIdProduto(id);
            produto.setAtivo(true);
            return PRODUTO_REPOSITORY.save(produto);
        }

        log.error("Falha ao atualizar produto: ID {} nao encontrado", id);
        throw new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, Domains.PRODUTO);
    }
}

