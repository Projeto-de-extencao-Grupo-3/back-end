package geo.track.controller;

import geo.track.controller.swagger.OficinaSwagger;
import geo.track.domain.Oficinas;
import geo.track.dto.autenticacao.UsuarioCriacaoDto;
import geo.track.dto.autenticacao.UsuarioLoginDto;
import geo.track.dto.autenticacao.UsuarioMapper;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.oficinas.request.OficinaPatchEmailDTO;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;
import geo.track.dto.oficinas.response.OficinaResponse;
import geo.track.mapper.OficinaMapper;
import geo.track.service.OficinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/oficinas")
@RequiredArgsConstructor
public class OficinaController implements OficinaSwagger {

    private final OficinaService OFICINA_SERVICE;
    public static class ExceptionBody { public int status; public String title; }

    @Override
    @PostMapping
    public ResponseEntity<Void> cadastrarEmpresa(@RequestBody @Valid UsuarioCriacaoDto empresa){
        log.info("Iniciando cadastro de nova empresa: {}", empresa.getRazaoSocial());
        final Oficinas novaEmpresa = UsuarioMapper.of(empresa);
        this.OFICINA_SERVICE.cadastrar(novaEmpresa);
        log.info("Empresa cadastrada com sucesso!");
        return ResponseEntity.status(201).build();
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto) {
        log.info("Tentativa de login para o usuário: {}", usuarioLoginDto.getEmail());
        UsuarioTokenDto usuarioTokenDto = this.OFICINA_SERVICE.autenticar(usuarioLoginDto);
        log.info("Login realizado com sucesso para: {}", usuarioLoginDto.getEmail());
        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<OficinaResponse>> listarEmpresas(){
        log.info("Listando todas as oficinas cadastradas.");
        List<Oficinas> lista = OFICINA_SERVICE.listar();
        log.info("Total de oficinas encontradas: {}", lista.size());
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(lista));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OficinaResponse> getEmpresaById(@PathVariable Integer id){
        log.info("Buscando oficina com ID: {}", id);
        Oficinas emp = OFICINA_SERVICE.findOficinasById(id);
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(emp));
    }

    @Override
    @GetMapping("/razao-social")
    public ResponseEntity<List<OficinaResponse>> getEmpresaByRazaoSocial(@PathVariable String razaoSocial){
        log.info("Buscando oficinas pela Razão Social: {}", razaoSocial);
        List<Oficinas> lista = OFICINA_SERVICE.findOficinasByRazaoSocial(razaoSocial);
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(lista));
    }

    @Override
    @GetMapping("/cnpj")
    public ResponseEntity<OficinaResponse> findEmpresaByCNPJ(@PathVariable String cnpj){
        log.info("Buscando oficina pelo CNPJ: {}", cnpj);
        Oficinas emp = OFICINA_SERVICE.findOficinasByCnpj(cnpj);
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(emp));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<OficinaResponse> atualizarEmpresa(@PathVariable Integer id, @RequestBody Oficinas empresa){
        log.info("Atualizando dados da oficina com ID: {}", id);
        Oficinas emp = OFICINA_SERVICE.atualizar(id, empresa);
        log.info("Oficina ID {} atualizada com sucesso.", id);
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(emp));
    }

    @Override
    @PatchMapping("/email")
    public ResponseEntity<OficinaResponse> patchEmail(@RequestBody OficinaPatchEmailDTO dto){
        log.info("Atualizando email da oficina ID: {}", dto.getId());
        Oficinas emp = OFICINA_SERVICE.patchEmail(dto);
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(emp));
    }

    @Override
    @PatchMapping("/status")
    public ResponseEntity<OficinaResponse> patchStatus(@RequestBody OficinaPatchStatusDTO dto){
        log.info("Alterando status da oficina ID: {}", dto.getId());
        Oficinas emp = OFICINA_SERVICE.patchStatus(dto);
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(emp));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerEmpresa(@PathVariable Integer id){
        log.warn("Solicitação para remover oficina com ID: {}", id);
        OFICINA_SERVICE.remover(id);
        log.info("Oficina ID {} removida com sucesso.", id);
        return ResponseEntity.status(204).build();
    }
}