package geo.track.controller;

import geo.track.domain.Empresas;
import geo.track.domain.Veiculos;
import geo.track.dto.autenticacao.UsuarioCriacaoDto;
import geo.track.dto.autenticacao.UsuarioLoginDto;
import geo.track.dto.autenticacao.UsuarioMapper;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.empresas.request.EmpresaPatchEmailDTO;
import geo.track.dto.empresas.request.EmpresaPatchStatusDTO;
import geo.track.dto.veiculos.request.RequestPatchCor;
import geo.track.dto.veiculos.request.RequestPatchPlaca;
import geo.track.service.EmpresasService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Empresas> cadastrarEmpresa(@RequestBody @Valid UsuarioCriacaoDto empresa){
//        Empresas emp = empresasService.cadastrar(empresa);
//        return ResponseEntity.status(201).body(emp);

        final Empresas novaEmpresa = UsuarioMapper.of(empresa);
        this.empresasService.cadastrar(novaEmpresa);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {

        final Empresas empresa = UsuarioMapper.of(usuarioLoginDto);
        UsuarioTokenDto usuarioTokenDto = this.empresasService.autenticar(empresa);

        return ResponseEntity.status(200).body(usuarioTokenDto);
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

    @PatchMapping("/email")
    public ResponseEntity<Empresas> patchEmail(@RequestBody EmpresaPatchEmailDTO dto){
        Empresas emp = empresasService.patchEmail(dto);
        return ResponseEntity.status(200).body(emp);
    }

    @PatchMapping("/status")
    public ResponseEntity<Empresas> patchStatus(@RequestBody EmpresaPatchStatusDTO dto){
        Empresas emp = empresasService.patchStatus(dto);
        return ResponseEntity.status(200).body(emp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empresas> removerEmpresa(@PathVariable Integer id){
        empresasService.remover(id);
        return ResponseEntity.status(204).build();
    }

}