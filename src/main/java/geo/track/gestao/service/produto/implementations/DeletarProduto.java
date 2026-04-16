package geo.track.gestao.service.produto.implementations;

import geo.track.gestao.entity.Produto;
import geo.track.gestao.entity.repository.ProdutoRepository;
import geo.track.gestao.service.ProdutoService;
import geo.track.gestao.service.produto.DeletarProdutoUseCase;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.service.ordemServico.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarProduto implements DeletarProdutoUseCase {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final ProdutoService PRODUTO_SERVICE;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final Log log;

    public void execute(Integer id) {
        // Verifica se existe alguma ordem de serviço na jornada associada a este item de produto antes de permitir a exclusão
        if (ORDEM_SERVICO_SERVICE.existeOrdemServicoAbertaUsandoProduto(id)) {
            log.warn("Falha ao deletar item de produto ID {}: existem ordens de serviço não finalizadas associadas", id);
            throw new RuntimeException(String.format("Item de produto ID %d não pode ser deletado: existem ordens de serviço não finalizadas associadas", id));
        }

        Produto produto = PRODUTO_SERVICE.buscarProdutosPorId(id);

        log.info("Excluindo produto ID: {}", id);
        produto.setAtivo(false);
        PRODUTO_REPOSITORY.save(produto);
    }
}

