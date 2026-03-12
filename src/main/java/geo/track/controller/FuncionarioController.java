package geo.track.controller;

import geo.track.controller.swagger.FuncionarioSwagger;
import geo.track.domain.Funcionario;
import geo.track.dto.funcionarios.FuncionarioResponse;
import geo.track.mapper.FuncionarioMapper;
import geo.track.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/funcionarios")
public class FuncionarioController implements FuncionarioSwagger {
    private final FuncionarioService FUNCIONARIO_SERVICE;

    @Override
    @PostMapping
    public ResponseEntity<FuncionarioResponse> cadastrar(@RequestBody Funcionario funcionario){
        Funcionario funcionarioResposta = FUNCIONARIO_SERVICE.cadastrar(funcionario);
        return ResponseEntity.status(201).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }


    @GetMapping
    public ResponseEntity<List<FuncionarioResponse>> findAllFuncionarios() {
        List<Funcionario> funcionarios = FUNCIONARIO_SERVICE.findFuncionarios();

        if (funcionarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<FuncionarioResponse> response = FuncionarioMapper.toResponse(funcionarios);
        return ResponseEntity.status(200).body(response);
    }

    @Override
    @GetMapping("/oficina/{idOficina}")
    public ResponseEntity<List<FuncionarioResponse>> findByOficina(@PathVariable Integer idOficina) {
        List<Funcionario> funcionarioResposta = FUNCIONARIO_SERVICE.buscarPorOficina(idOficina);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable Integer id){
        Funcionario funcionarioResposta = FUNCIONARIO_SERVICE.buscarPorId(id);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> atualizar(@PathVariable Integer id, @RequestBody Funcionario funcionario){
        Funcionario funcionarioResposta = FUNCIONARIO_SERVICE.atualizar(id, funcionario);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        FUNCIONARIO_SERVICE.deletar(id);
        return ResponseEntity.status(204).build();
    }
}