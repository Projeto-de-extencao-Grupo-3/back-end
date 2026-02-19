package geo.track.controller;

import geo.track.controller.swagger.ServicoSwagger;
import geo.track.domain.Servico;
import geo.track.dto.servicos.ServicoResponse;
import geo.track.mapper.ServicoMapper;
import geo.track.service.ServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoController implements ServicoSwagger {
    private final ServicoService SERVICO_SERVICE;

    @Override
    @PostMapping
    public ResponseEntity<ServicoResponse> cadastrar(@RequestBody Servico servico){
        Servico servicoResposta = SERVICO_SERVICE.cadastrar(servico);
        return ResponseEntity.status(201).body(ServicoMapper.toResponse(servicoResposta));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponse> buscarPorId(@PathVariable Integer id){
        Servico servicoResposta = SERVICO_SERVICE.buscarPorId(id);
        return ResponseEntity.status(200).body(ServicoMapper.toResponse(servicoResposta));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponse> atualizar(@PathVariable Integer id, @RequestBody Servico servico){
        Servico servicoResposta = SERVICO_SERVICE.atualizar(id, servico);
        return ResponseEntity.status(200).body(ServicoMapper.toResponse(servicoResposta));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        SERVICO_SERVICE.deletar(id);
        return ResponseEntity.status(204).build();
    }
}