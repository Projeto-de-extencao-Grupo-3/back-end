package geo.track.gestao;

import geo.track.controller.swagger.FuncionarioSwagger;
import geo.track.gestao.entity.Funcionario;
import geo.track.dto.funcionarios.request.RequestPostFuncionario;
import geo.track.dto.funcionarios.response.FuncionarioResponse;
import geo.track.dto.funcionarios.request.RequestPutFuncionario;
import geo.track.gestao.util.FuncionarioMapper;
import geo.track.service.FuncionarioService;
import jakarta.validation.Valid;
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
    public ResponseEntity<FuncionarioResponse> cadastrar(@RequestBody @Valid RequestPostFuncionario body){
        Funcionario funcionarioResposta = FUNCIONARIO_SERVICE.cadastrar(body);
        return ResponseEntity.status(201).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<FuncionarioResponse>> listarFuncionarios(){
        List<Funcionario> lista = FUNCIONARIO_SERVICE.listar();
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(lista));
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
    @PutMapping()
    public ResponseEntity<FuncionarioResponse> atualizar(@RequestBody @Valid RequestPutFuncionario body){
        Funcionario funcionarioResposta = FUNCIONARIO_SERVICE.atualizar(body);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        FUNCIONARIO_SERVICE.deletar(id);
        return ResponseEntity.status(204).build();
    }
}