package geo.track.gestao.funcionario.infraestructure.web;

import geo.track.gestao.funcionario.infraestructure.persistence.entity.Funcionario;
import geo.track.gestao.funcionario.infraestructure.request.RequestPostFuncionario;
import geo.track.gestao.funcionario.infraestructure.response.FuncionarioResponse;
import geo.track.gestao.funcionario.infraestructure.request.RequestPutFuncionario;
import geo.track.gestao.funcionario.application.AtualizarFuncionarioUseCase;
import geo.track.gestao.funcionario.application.CadastrarFuncionarioUseCase;
import geo.track.gestao.funcionario.application.DeletarFuncionarioUseCase;
import geo.track.gestao.funcionario.infraestructure.FuncionarioMapper;
import geo.track.gestao.funcionario.domain.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/funcionarios")
public class FuncionarioController implements FuncionarioSwagger {
    private final FuncionarioService FUNCIONARIO_SERVICE;
    private final CadastrarFuncionarioUseCase CADASTRAR_FUNCIONARIO_USECASE;
    private final AtualizarFuncionarioUseCase ATUALIZAR_FUNCIONARIO_USECASE;
    private final DeletarFuncionarioUseCase DELETAR_FUNCIONARIO_USECASE;

    @Override
    @PostMapping
    public ResponseEntity<FuncionarioResponse> cadastrar(@RequestBody @Valid RequestPostFuncionario body){
        Funcionario funcionarioResposta = CADASTRAR_FUNCIONARIO_USECASE.execute(body);
        return ResponseEntity.status(201).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<FuncionarioResponse>> listarFuncionarios(){
        List<Funcionario> lista = FUNCIONARIO_SERVICE.listarFuncionarios();
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(lista));
    }

    @Override
    @GetMapping("/busca/nome")
    public ResponseEntity<Page<FuncionarioResponse>> listarFuncionariosPorNomePaginado(
            @PageableDefault(size = 8, sort = "idFuncionario") Pageable pageable,
            @RequestParam(required = true) String nome
    ){
        Page<Funcionario> funcionarios = FUNCIONARIO_SERVICE.listarFuncionariosPorNomePaginado(pageable, nome);

        if (funcionarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        Page<FuncionarioResponse> response = funcionarios.map(FuncionarioMapper::toResponse);
        return ResponseEntity.status(200).body(response);
    }

    @Override
    @GetMapping("/funcionarios-paginados")
    public ResponseEntity<Page<FuncionarioResponse>> listarFuncionariosPaginados(
            @PageableDefault(size = 8, sort = "idFuncionario") Pageable pageable){
        Page<Funcionario> funcionarios = FUNCIONARIO_SERVICE.listarFuncionariosPaginados(pageable);

        Page<FuncionarioResponse> response = funcionarios.map(FuncionarioMapper::toResponse);

        if (response.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

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
    @PutMapping()
    public ResponseEntity<FuncionarioResponse> atualizar(@RequestBody @Valid RequestPutFuncionario body){
        Funcionario funcionarioResposta = ATUALIZAR_FUNCIONARIO_USECASE.execute(body);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        DELETAR_FUNCIONARIO_USECASE.execute(id);
        return ResponseEntity.status(204).build();
    }
}
