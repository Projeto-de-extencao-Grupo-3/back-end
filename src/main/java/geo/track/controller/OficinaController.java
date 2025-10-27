package geo.track.controller;

import geo.track.domain.Oficinas;
import geo.track.dto.autenticacao.UsuarioCriacaoDto;
import geo.track.dto.autenticacao.UsuarioLoginDto;
import geo.track.dto.autenticacao.UsuarioMapper;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.oficinas.request.OficinaPatchEmailDTO;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;
import geo.track.service.OficinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oficinas")
@RequiredArgsConstructor
@Tag(name = "Oficinas (Empresas)", description = "Endpoints utilizados para gerenciar Oficinas/Empresas.")
public class OficinaController {
    private final OficinaService oficinaService;
    static class ExceptionBody { public int status; public String title; }

    @Operation(summary = "Cadastrar uma nova Oficina/Empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Oficina cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de criação inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "409", description = "CNPJ/Email já cadastrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    public ResponseEntity<Oficinas> cadastrarEmpresa(@RequestBody @Valid UsuarioCriacaoDto empresa){
        final Oficinas novaEmpresa = UsuarioMapper.of(empresa);
        this.oficinaService.cadastrar(novaEmpresa);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Realizar login e obter Token de acesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso", content = @Content(schema = @Schema(implementation = UsuarioTokenDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        final Oficinas empresa = UsuarioMapper.of(usuarioLoginDto);
        UsuarioTokenDto usuarioTokenDto = this.oficinaService.autenticar(empresa);
        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @Operation(summary = "Listar todas as Oficinas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Oficinas retornada com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Oficinas.class)))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public ResponseEntity<List<Oficinas>> listarEmpresas(){
        List<Oficinas> lista = oficinaService.listar();
        return ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Buscar Oficina pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oficina encontrada com sucesso", content = @Content(schema = @Schema(implementation = Oficinas.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public ResponseEntity<Oficinas> getEmpresaById(@PathVariable Integer id){
        Oficinas emp = oficinaService.findOficinasById(id);
        return ResponseEntity.status(200).body(emp);
    }

    @Operation(summary = "Buscar Oficinas por Razão Social (busca parcial)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Oficinas encontrada com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Oficinas.class)))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/razao-social")
    public ResponseEntity<List<Oficinas>> getEmpresaByRazaoSocial(@RequestParam String razaoSocial){
        List<Oficinas> lista = oficinaService.findOficinasByRazaoSocial(razaoSocial);
        return ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Buscar Oficina por CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oficina encontrada com sucesso", content = @Content(schema = @Schema(implementation = Oficinas.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/cnpj")
    public ResponseEntity<Oficinas> findEmpresaByCNPJ(@RequestParam String cnpj){
        Oficinas emp = oficinaService.findOficinasByCnpj(cnpj);
        return ResponseEntity.status(200).body(emp);
    }

    @Operation(summary = "Atualizar todos os dados de uma Oficina (exceto campos sensíveis)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oficina atualizada com sucesso", content = @Content(schema = @Schema(implementation = Oficinas.class))),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    public ResponseEntity<Oficinas> atualizarEmpresa(@PathVariable Integer id, @RequestBody Oficinas empresa){
        Oficinas emp = oficinaService.atualizar(id, empresa);
        return ResponseEntity.status(200).body(emp);
    }

    @Operation(summary = "Atualizar o email de uma Oficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email atualizado com sucesso", content = @Content(schema = @Schema(implementation = Oficinas.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (Email já em uso ou inválido)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/email")
    public ResponseEntity<Oficinas> patchEmail(@RequestBody OficinaPatchEmailDTO dto){
        Oficinas emp = oficinaService.patchEmail(dto);
        return ResponseEntity.status(200).body(emp);
    }

    @Operation(summary = "Atualizar o status (ativo/inativo) de uma Oficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso", content = @Content(schema = @Schema(implementation = Oficinas.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/status")
    public ResponseEntity<Oficinas> patchStatus(@RequestBody OficinaPatchStatusDTO dto){
        Oficinas emp = oficinaService.patchStatus(dto);
        return ResponseEntity.status(200).body(emp);
    }

    @Operation(summary = "Remover uma Oficina pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Oficina removida com sucesso (Sem Conteúdo)"),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    public ResponseEntity<Oficinas> removerEmpresa(@PathVariable Integer id){
        oficinaService.remover(id);
        return ResponseEntity.status(204).build();
    }
}