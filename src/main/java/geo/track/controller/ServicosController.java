package geo.track.controller;

import geo.track.domain.Servicos;
import geo.track.exception.ExceptionBody; // Import necessário
import geo.track.service.ServicosService;
import io.swagger.v3.oas.annotations.Operation; // Import necessário
import io.swagger.v3.oas.annotations.media.Content; // Import necessário
import io.swagger.v3.oas.annotations.media.Schema; // Import necessário
import io.swagger.v3.oas.annotations.responses.ApiResponse; // Import necessário
import io.swagger.v3.oas.annotations.responses.ApiResponses; // Import necessário
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag; // Import necessário
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
@Tag(name = "Serviços", description = "Endpoints utilizados para gerenciar os serviços") // Adicionado
@SecurityRequirement(name = "Bearer")
public class ServicosController {

    private final ServicosService service;

    @Operation( // Adicionado
            summary = "Cadastrar novo serviço",
            description = "Recebe um objeto de serviço e o armazena, retornando o serviço criado."
    )
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "201",
                    description = "Serviço cadastrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Servicos.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para o serviço",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PostMapping
    public ResponseEntity<Servicos> cadastrar(@RequestBody Servicos servico){
        Servicos servicoResposta = service.cadastrar(servico);
        return ResponseEntity.status(201).body(servicoResposta);
    }

    @Operation(summary = "Buscar serviço pelo ID") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "200",
                    description = "Serviço encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Servicos.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Serviço não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionBody.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Servicos> buscarPorId(@PathVariable Integer id){
        Servicos servicoResposta = service.buscarPorId(id);
        return ResponseEntity.status(200).body(servicoResposta);
    }

    @Operation( // Adicionado
            summary = "Atualizar completamente um serviço",
            description = "Recebe o ID e um objeto de serviço com os novos dados para atualização."
    )
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "200",
                    description = "Serviço atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Servicos.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para a atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Serviço não encontrado para atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Servicos> atualizar(@PathVariable Integer id, @RequestBody Servicos servico){
        Servicos servicoResposta = service.atualizar(id, servico);
        return ResponseEntity.status(200).body(servicoResposta);
    }

    @Operation(summary = "Remover um serviço") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "204",
                    description = "Serviço removido com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Serviço não encontrado para remoção",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }
}