package geo.track.catalogo.produto.domain;

import geo.track.catalogo.produto.infraestructure.persistence.entity.Produto;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.Servico;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ProdutoExceptionMessages;
import geo.track.infraestructure.log.Log;
import geo.track.catalogo.produto.infraestructure.persistence.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository PRODUTO_REPOSITORY;
    private final Log log;

    public List<Produto> listarProdutos(){
        log.info("Listando todos os produtos");
        return PRODUTO_REPOSITORY.findByAtivoTrue();
    }

    public Page<Produto> listarProdutosPorNomePaginado(String nome, Pageable pageable){
        return PRODUTO_REPOSITORY.findByNomeContainingIgnoreCaseAndAtivoTrue(pageable, nome);
    }

    public Page<Produto> listarProdutosPorTipoServico(Servico tipo, Pageable pageable){
        return PRODUTO_REPOSITORY.findByTipoServicoAndAtivoTrue(tipo, pageable);
    }

    public Page<Produto> listarProdutosPaginados(Pageable pageable){
        return PRODUTO_REPOSITORY.findByAtivoTrue(pageable);
    }

    public HashMap<String, List<Produto>> listarChaveadaProdutosPorStatus(){
        log.info("Listando todos os produtos por status");
        HashMap<String, List<Produto>> response = new HashMap<>();

        List<Servico> tipoServicos = Arrays.stream(Servico.values()).toList();

        tipoServicos.forEach(t -> {
            response.put(t.name(), PRODUTO_REPOSITORY.findByTipoServico(t));
        });

        return response;
    }

    public Produto buscarProdutosPorId(Integer id) {
        log.info("Buscando produto com ID: {}", id);
        return PRODUTO_REPOSITORY.findByIdProdutoAndAtivoTrue(id).orElseThrow(
                () -> new DataNotFoundException(ProdutoExceptionMessages.PRODUTO_NAO_ENCONTRADO_ID, Domains.PRODUTO)
        );
    }
}
