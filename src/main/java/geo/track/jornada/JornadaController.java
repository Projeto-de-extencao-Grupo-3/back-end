package geo.track.jornada;

import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.dto.itensServicos.RequestPutItemServico;
import geo.track.dto.os.request.RequestPatchStatus;
import geo.track.gestao.service.itemservico.AdicionarItemServicoUseCase;
import geo.track.gestao.service.itemservico.AtualizarItemServicoUseCase;
import geo.track.gestao.service.itemservico.DeletarItemServicoUseCase;
import geo.track.gestao.service.produto.AdicionarItemProdutoUseCase;
import geo.track.gestao.service.produto.AtualizarItemProdutoUseCase;
import geo.track.gestao.service.produto.DeletarItemProdutoUseCase;
import geo.track.gestao.service.produto.RealizarBaixaEstoqueItemProdutoUseCase;
import geo.track.jornada.entity.OrdemDeServico;
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
import geo.track.jornada.service.ListagemService;
import geo.track.jornada.service.controle.*;
import geo.track.jornada.service.entrada.AgendamentoUseCase;
import geo.track.jornada.service.entrada.ConfirmacaoUseCase;
import geo.track.jornada.service.entrada.EntradaEfetivaSemCadastroUseCase;
import geo.track.jornada.service.entrada.EntradaEfetivaUseCase;
import geo.track.jornada.service.listagem.BuscaSimplesUseCase;
import geo.track.jornada.util.OrdemDeServicoMapper;
import geo.track.gestao.util.ItemProdutoMapper;
import geo.track.gestao.util.ItemServicoMapper;
import geo.track.jornada.util.RegistroEntradaMapper;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.request.entrada.RequestAgendamento;
import geo.track.jornada.request.entrada.RequestConfirmacao;
import geo.track.jornada.response.entrada.RegistroEntradaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jornada")
public class JornadaController implements JornadaSwagger {
    /**
     * Jornada: Entrada
     */
    private final AgendamentoUseCase agendamentoUseCase;
    private final ConfirmacaoUseCase confirmacaoUseCase;
    private final EntradaEfetivaUseCase entradaEfetivaUseCase;
    private final EntradaEfetivaSemCadastroUseCase entradaEfetivaSemCadastroUseCase;

    @Override
    @PostMapping("/agendamento")
    public ResponseEntity<RegistroEntradaResponse> agendamentoEntrada(@Valid @RequestBody RequestAgendamento request) {
        RegistroEntrada agendamento = agendamentoUseCase.execute(request.dataEntradaPrevista(), request.fkVeiculo());

        return ResponseEntity.status(201).body(RegistroEntradaMapper.toResponse(agendamento));
    }

    @Override
    @PatchMapping("/confirmar-entrada")
    public ResponseEntity<RegistroEntradaResponse> confirmarEntradaAgendada(@Valid @RequestBody RequestConfirmacao request) {
        RegistroEntrada entradaConfirmada = confirmacaoUseCase.execute(request.fkRegistro(), request.entrada());

        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(entradaConfirmada));
    }

    @Override
    @PostMapping("/entrada-efetiva")
    public ResponseEntity<RegistroEntradaResponse> entradaVeiculoEfetiva(@Valid @RequestBody RequestEntradaEfetiva request) {
        RegistroEntrada entradaFeita = entradaEfetivaUseCase.execute(request.fkVeiculo(), request.entrada());

        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(entradaFeita));
    }

    @Override
    @PostMapping("/entrada-efetiva-sem-cadastro")
    public ResponseEntity<RegistroEntradaResponse> entradaVeiculoSemCadastroEfetiva(@RequestBody RequestEntradaEfetivaSemCadastro request) {
        RegistroEntrada entradaFeita = entradaEfetivaSemCadastroUseCase.execute(request.veiculo(), request.entrada());

        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(entradaFeita));
    }

    /**
     * Jornada: Itens
     */
    private final AdicionarItemServicoUseCase adicionarItemServicoUseCase;
    private final AdicionarItemProdutoUseCase adicionarItemProdutoUseCase;
    private final AtualizarItemServicoUseCase atualizarItemServicoUseCase;
    private final AtualizarItemProdutoUseCase atualizarItemProdutoUseCase;
    private final DeletarItemServicoUseCase deletarItemServicoUseCase;
    private final DeletarItemProdutoUseCase deletarItemProdutoUseCase;
    private final RealizarBaixaEstoqueItemProdutoUseCase realizarBaixaEstoqueProdutoUseCase;

    @PostMapping("/{idOrdemServico}/produtos")
    public ResponseEntity<ItemProdutoResponse> adicionarItem(@PathVariable Integer idOrdemServico, @Valid @RequestBody RequestPostItemProduto request) {
        ItemProduto itemProduto = adicionarItemProdutoUseCase.execute(idOrdemServico, request);

        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(itemProduto));
    }

    @PutMapping("/{idItemProduto}/produtos")
    public ResponseEntity<ItemProdutoResponse> atualizarItem(@PathVariable Integer idItemProduto, @Valid @RequestBody RequestPutItemProduto request) {
        ItemProduto itemProduto = atualizarItemProdutoUseCase.execute(idItemProduto, request);

        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(itemProduto));
    }

    @DeleteMapping("/{idItemProduto}/produtos")
    public ResponseEntity<Object> deletarItemProduto(@PathVariable Integer idItemProduto) {
        deletarItemProdutoUseCase.execute(idItemProduto);

        return ResponseEntity.status(204).body(null);
    }

    @PostMapping("/{idOrdemServico}/servicos")
    public ResponseEntity<ItemServicoResponse> adicionarItem(@PathVariable Integer idOrdemServico, @Valid @RequestBody RequestPostItemServico request) {
        ItemServico itemServico = adicionarItemServicoUseCase.execute(idOrdemServico, request);

        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }

    @PatchMapping("/{idItemProduto}/saida-material")
    public ResponseEntity<Object> realizarSaidaMaterial(@PathVariable Integer idItemProduto) {
        realizarBaixaEstoqueProdutoUseCase.execute(idItemProduto);

        return ResponseEntity.status(204).body(null);
    }

    @PutMapping("/{idItemServico}/servicos")
    public ResponseEntity<ItemServicoResponse> atualizarItem(@PathVariable Integer idItemServico, @Valid @RequestBody RequestPutItemServico request) {
        ItemServico itemServico = atualizarItemServicoUseCase.execute(idItemServico, request);

        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }

    @DeleteMapping("/{idItemServico}/servicos")
    public ResponseEntity<Object> deletarItemServico(@PathVariable Integer idItemServico) {
        deletarItemServicoUseCase.execute(idItemServico);

        return ResponseEntity.status(204).body(null);
    }

    /**
     * Jornada: Listagem
     */
    private final ListagemService listagemService;
    private final BuscaSimplesUseCase buscaSimplesUseCase;

    @GetMapping("/listagem")
    public ResponseEntity<Object> listarOrdensJornada(@ParameterObject @Valid ListagemJornadaParams params) {
        var response = listagemService.execute(params);

        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/listagem/{id}")
    public ResponseEntity<ListagemJornadaResponse> busscarOrdemJornada(@PathVariable Integer id) {
        ListagemJornadaResponse response = buscaSimplesUseCase.execute(id);

        return ResponseEntity.status(200).body(response);
    }

    /**
     * Jornada: Controle
     */
    private final AtualizarStatusUseCase atualizarStatusUseCase;
    private final CancelarOrdemUseCase cancelarOrdemUseCase;
    private final DefinirDataSaidaPrevistaUseCase definirDataSaidaPrevistaImplementationUseCase;
    private final DefinirPagamentoRealizadoUseCase definirPagamentoRealizadoUseCase;
    private final DefinirNotaFiscalRealizadaUseCase definirNotaFiscalRealizadaUseCase;

    @PatchMapping("/{idOrdemServico}/atualizar-status")
    public ResponseEntity<OrdemDeServicoResponse> atualizarStatus(@PathVariable Integer idOrdemServico, @RequestBody @Valid RequestPatchStatus request) {
        OrdemDeServico ordem = atualizarStatusUseCase.execute(idOrdemServico, request.getStatus());

        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @PatchMapping("/{idOrdemServico}/definir-data-saida-prevista")
    public ResponseEntity<OrdemDeServicoResponse> definirDataSaidaPrevista(@PathVariable Integer idOrdemServico, @RequestBody @Valid RequestPatchSaidaPrevista request) {
        OrdemDeServico ordem = definirDataSaidaPrevistaImplementationUseCase.execute(idOrdemServico, request.saidaPrevista());

        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @PatchMapping("/{idOrdemServico}/definir-pagamento-realizado")
    public ResponseEntity<OrdemDeServicoResponse> definirPagamentoRealizado(@PathVariable Integer idOrdemServico) {
        OrdemDeServico ordem = definirPagamentoRealizadoUseCase.execute(idOrdemServico);

        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @PatchMapping("/{idOrdemServico}/definir-nota-fiscal-realizada")
    public ResponseEntity<OrdemDeServicoResponse> definirNotaFiscalRealizada(@PathVariable Integer idOrdemServico) {
        OrdemDeServico ordem = definirNotaFiscalRealizadaUseCase.execute(idOrdemServico);

        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @DeleteMapping("/{idOrdemServico}/cancelar-ordem")
    public ResponseEntity<OrdemDeServicoResponse> cancelarOrdem(@PathVariable Integer idOrdemServico) {
        cancelarOrdemUseCase.execute(idOrdemServico);

        return ResponseEntity.status(204).body(null);
    }

}
