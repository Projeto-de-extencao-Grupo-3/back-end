package geo.track.gestao;

import geo.track.dto.contatos.request.RequestPostContato;
import geo.track.dto.contatos.request.RequestPutContato;
import geo.track.dto.contatos.response.ContatoResponse;
import geo.track.gestao.entity.Contato;
import geo.track.gestao.service.ContatoService;
import geo.track.gestao.service.contato.AtualizarContatoUseCase;
import geo.track.gestao.service.contato.CadastrarContatoUseCase;
import geo.track.gestao.service.contato.DeletarContatoUseCase;
import geo.track.gestao.util.ContatoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes/{idCliente}/contatos")
@RequiredArgsConstructor
public class ContatoController {
    private final ContatoService CONTATO_SERVICE;
    private final CadastrarContatoUseCase CADASTRAR_CONTATO_USECASE;
    private final AtualizarContatoUseCase ATUALIZAR_CONTATO_USECASE;
    private final DeletarContatoUseCase DELETAR_CONTATO_USECASE;

    @GetMapping("/{idContato}")
    public ResponseEntity<ContatoResponse> getContatoById(@PathVariable Integer idCliente, @PathVariable Integer idContato) {
        Contato contato = CONTATO_SERVICE.buscarContatoPorId(idCliente, idContato);
        return ResponseEntity.status(200).body(ContatoMapper.toResponse(contato));
    }

    @PostMapping
    public ResponseEntity<ContatoResponse> postContato(@PathVariable Integer idCliente, @RequestBody @Valid RequestPostContato body) {
        Contato contato = CADASTRAR_CONTATO_USECASE.execute(idCliente, body);
        return ResponseEntity.status(201).body(ContatoMapper.toResponse(contato));
    }

    @PutMapping("/{idContato}")
    public ResponseEntity<ContatoResponse> putContato(
            @PathVariable Integer idCliente,
            @PathVariable Integer idContato,
            @RequestBody @Valid RequestPutContato body
    ) {
        Contato contato = ATUALIZAR_CONTATO_USECASE.execute(idCliente, idContato, body);
        return ResponseEntity.status(200).body(ContatoMapper.toResponse(contato));
    }

    @DeleteMapping("/{idContato}")
    public ResponseEntity<Void> deleteContato(@PathVariable Integer idCliente, @PathVariable Integer idContato) {
        DELETAR_CONTATO_USECASE.execute(idCliente, idContato);
        return ResponseEntity.status(204).build();
    }
}

