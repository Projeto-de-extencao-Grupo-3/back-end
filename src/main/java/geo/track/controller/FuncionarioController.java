package geo.track.controller;

import geo.track.domain.Funcionarios;
import geo.track.service.FuncionariosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionariosService service;

    public FuncionarioController(FuncionariosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Funcionarios> cadastrar(@RequestBody Funcionarios funcionario){
        return service.cadastrar(funcionario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionarios> buscarPorId(@PathVariable Integer id){
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionarios> atualizar(@PathVariable Integer id, @RequestBody Funcionarios funcionario){
        return service.atualizar(id, funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        return service.deletar(id);
    }
}
