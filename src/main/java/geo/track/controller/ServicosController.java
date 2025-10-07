package geo.track.controller;

import geo.track.domain.Servicos;
import geo.track.service.ServicosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos")
public class ServicosController {

    private final ServicosService service;

    public ServicosController(ServicosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Servicos> cadastrar(@RequestBody Servicos servico){
        Servicos servicoResposta = service.cadastrar(servico);
        return ResponseEntity.status(201).body(servicoResposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicos> buscarPorId(@PathVariable Integer id){
        Servicos servicoResposta = service.buscarPorId(id);
        return ResponseEntity.status(200).body(servicoResposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicos> atualizar(@PathVariable Integer id, @RequestBody Servicos servico){
        Servicos servicoResposta = service.atualizar(id, servico);
        return ResponseEntity.status(200).body(servicoResposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
