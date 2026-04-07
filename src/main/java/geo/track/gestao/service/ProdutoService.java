package geo.track.gestao.service;

import geo.track.gestao.entity.Produto;
import geo.track.gestao.enums.Servico;
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
    private final Log log;

    public List<Produto> listarProdutos(){
        log.info("Listando todos os produtos");
        return PRODUTO_REPOSITORY.findByAtivoTrue();
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
