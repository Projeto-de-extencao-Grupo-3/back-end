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
        Empresas emp = empresasService.cadastrar(empresa);
        return ResponseEntity.status(201).body(emp);
    }

    @GetMapping
    public ResponseEntity<List<Empresas>> listarEmpresas(){
        List<Empresas> lista = empresasService.listar();
        return ResponseEntity.status(200).body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresas> getEmpresaById(@PathVariable Integer id){
        Empresas emp = empresasService.findEmpresaById(id);
        return ResponseEntity.status(200).body(emp);
    }

    @GetMapping("/razao-social")
    public ResponseEntity<List<Empresas>> getEmpresaByRazaoSocial(@RequestParam String razaoSocial){
        List<Empresas> lista = empresasService.findEmpresaByRazaoSocial(razaoSocial);
        return ResponseEntity.status(200).body(lista);
    }

    @GetMapping("/cnpj")
    public ResponseEntity<Empresas> findEmpresaByCNPJ(@RequestParam String cnpj){
        Empresas emp = empresasService.findEmpresaByCnpj(cnpj);
        return ResponseEntity.status(200).body(emp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresas> atualizarEmpresa(@PathVariable Integer id, @RequestBody Empresas empresa){
        Empresas emp = empresasService.atualizar(id, empresa);
        return ResponseEntity.status(200).body(emp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empresas> removerEmpresa(@PathVariable Integer id){
        empresasService.remover(id);
        return ResponseEntity.status(204).build();
    }

}