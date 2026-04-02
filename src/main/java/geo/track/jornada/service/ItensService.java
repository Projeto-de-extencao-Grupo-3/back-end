package geo.track.jornada.service;

import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.gestao.service.itemservico.AdicionarItemServicoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Esta service tem a função de transformar o objeto recebido da controller em um request especifico para cada use case, e depois transformar o response do use case em um response específico para a controller.
 */
@Service
@RequiredArgsConstructor
public class ItensService {
    private final AdicionarItemServicoUseCase adicionarItemServicoUseCase;

    public ResponseEntity<ItemProdutoResponse> adicionarItemProduto() {
        return null;
    }

    public ResponseEntity<ItemProdutoResponse> atualizarItemProduto() {
        return null;

    }

    public ResponseEntity<ItemProdutoResponse> deletarItemProduto() {
        return null;
    }

    public ResponseEntity<ItemServicoResponse> adicionarItemServico() {
        return null;

    }

    public ResponseEntity<ItemProdutoResponse> realizarSaidaMaterial() {
        return null;
    }

    public ResponseEntity<ItemServicoResponse> atualizarItemServico() {
        return null;

    }

    public ResponseEntity<ItemServicoResponse> deletarItemServico() {
        return null;
    }
}
