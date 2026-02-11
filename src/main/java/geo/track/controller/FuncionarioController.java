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

    private final FuncionarioService service;

    @Override
    @PostMapping
    public ResponseEntity<FuncionarioResponse> cadastrar(@RequestBody Funcionario funcionario){
        Funcionario funcionarioResposta = service.cadastrar(funcionario);
        return ResponseEntity.status(201).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Override
    @GetMapping("/oficina/{idOficina}")
    public ResponseEntity<List<FuncionarioResponse>> findByOficina(@PathVariable Integer idOficina) {
        List<Funcionario> funcionarioResposta = service.buscarPorOficina(idOficina);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable Integer id){
        Funcionario funcionarioResposta = service.buscarPorId(id);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> atualizar(@PathVariable Integer id, @RequestBody Funcionario funcionario){
        Funcionario funcionarioResposta = service.atualizar(id, funcionario);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }
}