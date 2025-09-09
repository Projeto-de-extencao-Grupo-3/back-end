package geo.track.controller;

import geo.track.domain.Empresas;
import geo.track.service.EmpresasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {
    public final EmpresasService empresasService;

    public EmpresaController(EmpresasService service) {
        this.empresasService = service;
    }

    @PostMapping
    public ResponseEntity<Empresas> cadastrarEmpresa(@RequestBody Empresas empresa){
        return empresasService.cadastrar(empresa);
    }

    @GetMapping
    public ResponseEntity<List<Empresas>> listarEmpresas(){
        return empresasService.listar();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresas> atualizarEmpresa(@PathVariable Integer id, @RequestBody Empresas empresa){
        return empresasService.atualizar(id, empresa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empresas> removerEmpresa(@PathVariable Integer id){
        return empresasService.remover(id);
    }

}