package geo.track.controller;

import geo.track.domain.Funcionarios;
import geo.track.repository.FuncionariosRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionariosRepository repository;

    public FuncionarioController(FuncionariosRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Funcionarios> cadastrar(@RequestBody Funcionarios funcionario){
        if (repository.existsByNome(funcionario.getNome())){
            return ResponseEntity.status(409).build();
        }
        Funcionarios funcionarioRegistrado = repository.save(funcionario);
        return ResponseEntity.status(201).body(funcionarioRegistrado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionarios> buscarPorId(@PathVariable Integer id){
        Optional<Funcionarios> funcionarioOp = repository.findById(id);

        if (funcionarioOp.isPresent()){
            Funcionarios funcionario = funcionarioOp.get();
            return ResponseEntity.status(200).body(funcionario);
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionarios> atualizar(@PathVariable Integer id, @RequestBody Funcionarios funcionario){
        funcionario.setIdFuncionario(id);
        if (repository.existsById(id)){
            Funcionarios save = repository.save(funcionario);
            return ResponseEntity.status(200).body(save);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Funcionarios> deletar(@PathVariable Integer id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
