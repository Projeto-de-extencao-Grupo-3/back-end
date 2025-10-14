package geo.track.controller;

import geo.track.domain.Funcionarios;
import geo.track.service.FuncionariosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionariosService service;

    @PostMapping
    public ResponseEntity<Funcionarios> cadastrar(@RequestBody Funcionarios funcionario){
        Funcionarios funcionarioResposta = service.cadastrar(funcionario);
        return ResponseEntity.status(201).body(funcionarioResposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionarios> buscarPorId(@PathVariable Integer id){
        Funcionarios funcionarioResposta = service.buscarPorId(id);
        return ResponseEntity.status(200).body(funcionarioResposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionarios> atualizar(@PathVariable Integer id, @RequestBody Funcionarios funcionario){
        Funcionarios funcionarioResposta = service.atualizar(id, funcionario);
        return ResponseEntity.status(200).body(funcionarioResposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
