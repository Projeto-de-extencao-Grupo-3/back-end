package geo.track.gestao.cliente.infraestructure.web;

import geo.track.gestao.cliente.infraestructure.request.contato.RequestPostContato;
import geo.track.gestao.cliente.infraestructure.request.contato.RequestPutContato;
import geo.track.gestao.cliente.infraestructure.response.contato.ContatoResponse;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Contato;
import geo.track.gestao.cliente.domain.contato.ContatoService;
import geo.track.gestao.cliente.application.contato.AtualizarContatoUseCase;
import geo.track.gestao.cliente.application.contato.CadastrarContatoUseCase;
import geo.track.gestao.cliente.application.contato.DeletarContatoUseCase;
import geo.track.gestao.cliente.infraestructure.ContatoMapper;
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

