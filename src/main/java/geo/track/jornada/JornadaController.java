package geo.track.jornada;

import geo.track.domain.ItemProduto;
import geo.track.domain.ItemServico;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.jornada.request.entrada.RequestEntradaEfetiva;
import geo.track.jornada.request.entrada.RequestEntradaEfetivaSemCadastro;
import geo.track.jornada.request.itens.RequestPostItemProduto;
import geo.track.jornada.request.itens.RequestPostItemServico;
import geo.track.mapper.ItemProdutoMapper;
import geo.track.mapper.ItemServicoMapper;
import geo.track.mapper.RegistroEntradaMapper;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.request.entrada.RequestAgendamento;
import geo.track.jornada.request.entrada.RequestConfirmacao;
import geo.track.jornada.response.entrada.RegistroEntradaResponse;
import geo.track.jornada.service.JornadaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jornada")
public class JornadaController implements JornadaSwagger {
    private final JornadaService service;

    @Override
    @PostMapping("/agendamento")
    public ResponseEntity<RegistroEntradaResponse> agendamentoEntrada(@Valid @RequestBody RequestAgendamento request) {
        RegistroEntrada agendamento = service.realizarJornadaEntrada(request);

        return ResponseEntity.status(201).body(RegistroEntradaMapper.toResponse(agendamento));
    }

    @Override
    @PatchMapping("/confirmar-entrada")
    public ResponseEntity<RegistroEntradaResponse> confirmarEntradaAgendada(@Valid @RequestBody RequestConfirmacao request) {
        RegistroEntrada entradaConfirmada = service.realizarJornadaEntrada(request);

        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(entradaConfirmada));
    }

    @Override
    @PostMapping("/entrada-efetiva")
    public ResponseEntity<RegistroEntradaResponse> entradaVeiculoEfetiva(@Valid @RequestBody RequestEntradaEfetiva request) {
        RegistroEntrada entradaFeita = service.realizarJornadaEntrada(request);

        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(entradaFeita));
    }

    @Override
    @PostMapping("/entrada-efetiva-sem-cadastro")
    public ResponseEntity<RegistroEntradaResponse> entradaVeiculoSemCadastroEfetiva(@RequestBody RequestEntradaEfetivaSemCadastro request) {
        RegistroEntrada entradaFeita = service.realizarJornadaEntrada(request);

        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(entradaFeita));
    }

    @PostMapping("/{idOrdemServico}/produtos")
    public ResponseEntity<ItemProdutoResponse> adicionarItem(@PathVariable Integer idOrdemServico, @Valid @RequestBody RequestPostItemProduto request) {
        ItemProduto itemProduto = service.realizarJornadaItens(idOrdemServico, request);

        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(itemProduto));
    }

    @PostMapping("/{idOrdemServico}/servicos")
    public ResponseEntity<ItemServicoResponse> adicionarItem(@PathVariable Integer idOrdemServico, @Valid @RequestBody RequestPostItemServico request) {
        ItemServico itemServico = service.realizarJornadaItens(idOrdemServico, request);

        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }
}
