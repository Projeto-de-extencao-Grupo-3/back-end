package geo.track.service;

import geo.track.gestao.entity.Produto;
import geo.track.dto.produtos.ProdutoRequest;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.gestao.enums.Servico;
import geo.track.gestao.service.produto.AlterarPrecoCompraProdutoUseCase;
import geo.track.gestao.service.produto.AlterarPrecoVendaProdutoUseCase;
import geo.track.gestao.service.produto.AlterarQuantidadeEstoqueProdutoUseCase;
import geo.track.gestao.service.produto.AtualizarProdutoUseCase;
import geo.track.gestao.service.produto.CadastrarProdutoUseCase;
import geo.track.gestao.service.produto.DeletarProdutoUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.entity.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final CadastrarProdutoUseCase CADASTRAR_PRODUTO_USECASE;
    private final AtualizarProdutoUseCase ATUALIZAR_PRODUTO_USECASE;
    private final AlterarQuantidadeEstoqueProdutoUseCase ALTERAR_QUANTIDADE_ESTOQUE_PRODUTO_USECASE;
    private final AlterarPrecoCompraProdutoUseCase ALTERAR_PRECO_COMPRA_PRODUTO_USECASE;
    private final AlterarPrecoVendaProdutoUseCase ALTERAR_PRECO_VENDA_PRODUTO_USECASE;
    private final DeletarProdutoUseCase DELETAR_PRODUTO_USECASE;
    private final Log log;

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
}
