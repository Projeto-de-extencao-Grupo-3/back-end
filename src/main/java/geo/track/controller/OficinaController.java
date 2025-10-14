package geo.track.controller;

import geo.track.domain.Oficinas;
import geo.track.dto.autenticacao.UsuarioCriacaoDto;
import geo.track.dto.autenticacao.UsuarioLoginDto;
import geo.track.dto.autenticacao.UsuarioMapper;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.oficinas.request.OficinaPatchEmailDTO;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;
import geo.track.service.OficinaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oficinas")
@RequiredArgsConstructor
public class OficinaController {
    private final OficinaService oficinaService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Oficinas> cadastrarEmpresa(@RequestBody @Valid UsuarioCriacaoDto empresa){
//        Empresas emp = oficinaService.cadastrar(empresa);
//        return ResponseEntity.status(201).body(emp);

        final Oficinas novaEmpresa = UsuarioMapper.of(empresa);
        this.oficinaService.cadastrar(novaEmpresa);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {

        final Oficinas empresa = UsuarioMapper.of(usuarioLoginDto);
        UsuarioTokenDto usuarioTokenDto = this.oficinaService.autenticar(empresa);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }



    @GetMapping
    public ResponseEntity<List<Oficinas>> listarEmpresas(){
        List<Oficinas> lista = oficinaService.listar();
        return ResponseEntity.status(200).body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oficinas> getEmpresaById(@PathVariable Integer id){
        Oficinas emp = oficinaService.findEmpresaById(id);
        return ResponseEntity.status(200).body(emp);
    }

    @GetMapping("/razao-social")
    public ResponseEntity<List<Oficinas>> getEmpresaByRazaoSocial(@RequestParam String razaoSocial){
        List<Oficinas> lista = oficinaService.findEmpresaByRazaoSocial(razaoSocial);
        return ResponseEntity.status(200).body(lista);
    }

    @GetMapping("/cnpj")
    public ResponseEntity<Oficinas> findEmpresaByCNPJ(@RequestParam String cnpj){
        Oficinas emp = oficinaService.findEmpresaByCnpj(cnpj);
        return ResponseEntity.status(200).body(emp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Oficinas> atualizarEmpresa(@PathVariable Integer id, @RequestBody Oficinas empresa){
        Oficinas emp = oficinaService.atualizar(id, empresa);
        return ResponseEntity.status(200).body(emp);
    }

    @PatchMapping("/email")
    public ResponseEntity<Oficinas> patchEmail(@RequestBody OficinaPatchEmailDTO dto){
        Oficinas emp = oficinaService.patchEmail(dto);
        return ResponseEntity.status(200).body(emp);
    }

    @PatchMapping("/status")
    public ResponseEntity<Oficinas> patchStatus(@RequestBody OficinaPatchStatusDTO dto){
        Oficinas emp = oficinaService.patchStatus(dto);
        return ResponseEntity.status(200).body(emp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Oficinas> removerEmpresa(@PathVariable Integer id){
        oficinaService.remover(id);
        return ResponseEntity.status(204).build();
    }

}