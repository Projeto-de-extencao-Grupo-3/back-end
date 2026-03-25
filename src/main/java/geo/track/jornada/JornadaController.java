package geo.track.jornada;

import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.dto.itensServicos.RequestPutItemServico;
import geo.track.dto.os.request.RequestPatchStatus;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.request.controle.RequestPatchSaidaPrevista;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;
import geo.track.gestao.entity.ItemProduto;
import geo.track.gestao.entity.ItemServico;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.jornada.request.ListagemJornadaParams;
import geo.track.jornada.request.entrada.RequestEntradaEfetiva;
import geo.track.jornada.request.entrada.RequestEntradaEfetivaSemCadastro;
import geo.track.jornada.request.itens.RequestPostItemProduto;
import geo.track.jornada.request.itens.RequestPostItemServico;
import geo.track.jornada.response.listagem.OrdemDeServicoResponse;
import geo.track.jornada.service.ControleService;
import geo.track.jornada.service.ItensService;
import geo.track.jornada.service.ListagemService;
import geo.track.jornada.util.OrdemDeServicoMapper;
import geo.track.gestao.util.ItemProdutoMapper;
import geo.track.gestao.util.ItemServicoMapper;
import geo.track.jornada.util.RegistroEntradaMapper;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.request.entrada.RequestAgendamento;
import geo.track.jornada.request.entrada.RequestConfirmacao;
import geo.track.jornada.response.entrada.RegistroEntradaResponse;
import geo.track.jornada.service.EntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jornada")
public class JornadaController implements JornadaSwagger {
    private final EntradaService entradaService;

    /**
     * Jornada: Entrada
     */
    @Override
    @PostMapping("/agendamento")
    public ResponseEntity<RegistroEntradaResponse> agendamentoEntrada(@Valid @RequestBody RequestAgendamento request) {
        RegistroEntrada agendamento = entradaService.realizarJornadaEntrada(request);

        return ResponseEntity.status(201).body(RegistroEntradaMapper.toResponse(agendamento));
    }

    @Override
    @PatchMapping("/confirmar-entrada")
    public ResponseEntity<RegistroEntradaResponse> confirmarEntradaAgendada(@Valid @RequestBody RequestConfirmacao request) {
        RegistroEntrada entradaConfirmada = entradaService.realizarJornadaEntrada(request);

        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(entradaConfirmada));
    }

    @Override
    @PostMapping("/entrada-efetiva")
    public ResponseEntity<RegistroEntradaResponse> entradaVeiculoEfetiva(@Valid @RequestBody RequestEntradaEfetiva request) {
        RegistroEntrada entradaFeita = entradaService.realizarJornadaEntrada(request);

        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(entradaFeita));
    }

    @Override
    @PostMapping("/entrada-efetiva-sem-cadastro")
    public ResponseEntity<RegistroEntradaResponse> entradaVeiculoSemCadastroEfetiva(@RequestBody RequestEntradaEfetivaSemCadastro request) {
        RegistroEntrada entradaFeita = entradaService.realizarJornadaEntrada(request);

        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(entradaFeita));
    }

    /**
     * Jornada: Itens
     */
    private final ItensService itensService;

    @PostMapping("/{idOrdemServico}/produtos")
    public ResponseEntity<ItemProdutoResponse> adicionarItem(@PathVariable Integer idOrdemServico, @Valid @RequestBody RequestPostItemProduto request) {
        ItemProduto itemProduto = itensService.realizarJornadaItens(idOrdemServico, request);

        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(itemProduto));
    }

    @PutMapping("/{idItemProduto}/produtos")
    public ResponseEntity<ItemProdutoResponse> atualizarItem(@PathVariable Integer idItemProduto, @Valid @RequestBody RequestPutItemProduto request) {
        ItemProduto itemProduto = itensService.realizarJornadaItens(idItemProduto, request);

        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(itemProduto));
    }

    @DeleteMapping("/{idItemProduto}/produtos")
    public ResponseEntity<ItemProdutoResponse> deletarItemProduto(@PathVariable Integer idItemProduto) {
        ItemProduto itemProduto = itensService.realizarJornadaItens(idItemProduto, () -> TipoJornada.DELETAR_ITEM_PRODUTO);

        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(itemProduto));
    }

    @PostMapping("/{idOrdemServico}/servicos")
    public ResponseEntity<ItemServicoResponse> adicionarItem(@PathVariable Integer idOrdemServico, @Valid @RequestBody RequestPostItemServico request) {
        ItemServico itemServico = itensService.realizarJornadaItens(idOrdemServico, request);

        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }

    @PatchMapping("/{idItemProduto}/saida-material")
    public ResponseEntity<ItemProdutoResponse> realizarSaidaMaterial(@PathVariable Integer idItemProduto) {
        ItemProduto itemProduto = itensService.realizarJornadaItens(idItemProduto, () -> TipoJornada.SAIDA_MATERIAL);

        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(itemProduto));
    }

    @PutMapping("/{idItemServico}/servicos")
    public ResponseEntity<ItemServicoResponse> atualizarItem(@PathVariable Integer idItemServico, @Valid @RequestBody RequestPutItemServico request) {
        ItemServico itemServico = itensService.realizarJornadaItens(idItemServico, request);

        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }

    @DeleteMapping("/{idItemServico}/servicos")
    public ResponseEntity<ItemServicoResponse> deletarItemServico(@PathVariable Integer idItemServico) {
        ItemServico itemServico = itensService.realizarJornadaItens(idItemServico, () -> TipoJornada.DELETAR_ITEM_SERVICO);

        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }

    /**
     * Jornada: Listagem
     */
    private final ListagemService listagemService;

    @GetMapping("/listagem")
    public ResponseEntity<ListagemJornadaResponse> listarOrdensJornada(@ParameterObject @Valid ListagemJornadaParams params) {
        ListagemJornadaResponse response = listagemService.execute(params);

        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/listagem/{id}")
    public ResponseEntity<ListagemJornadaResponse> busscarOrdemJornada(@PathVariable Integer id) {
        ListagemJornadaResponse response = listagemService.execute(id);

        return ResponseEntity.status(200).body(response);
    }

    /**
     * Jornada: Controle
     */
    private final ControleService controleService;

    @PatchMapping("/{idOrdemServico}/atualizar-status")
    public ResponseEntity<OrdemDeServicoResponse> atualizarStatus(@PathVariable Integer idOrdemServico, @RequestBody @Valid RequestPatchStatus request) {
        OrdemDeServico ordem = controleService.realizarJornadaControle(idOrdemServico, request);

        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @PatchMapping("/{idOrdemServico}/definir-data-saida-prevista")
    public ResponseEntity<OrdemDeServicoResponse> definirDataSaidaPrevista(@PathVariable Integer idOrdemServico, @RequestBody @Valid RequestPatchSaidaPrevista request) {
        OrdemDeServico ordem = controleService.realizarJornadaControle(idOrdemServico, request);

        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @PatchMapping("/{idOrdemServico}/definir-pagamento-realizado")
    public ResponseEntity<OrdemDeServicoResponse> definirPagamentoRealizado(@PathVariable Integer idOrdemServico) {
        OrdemDeServico ordem = controleService.realizarJornadaControle(idOrdemServico, () -> TipoJornada.DEFINIR_PAGAMENTO_REALIZADO);

        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @PatchMapping("/{idOrdemServico}/definir-nota-fiscal-realizada")
    public ResponseEntity<OrdemDeServicoResponse> definirNotaFiscalRealizada(@PathVariable Integer idOrdemServico) {
        OrdemDeServico ordem = controleService.realizarJornadaControle(idOrdemServico, () -> TipoJornada.DEFINIR_NOTA_FISCAL_REALIZADA);

        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }
}
