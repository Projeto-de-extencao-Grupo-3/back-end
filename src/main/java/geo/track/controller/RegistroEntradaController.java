package geo.track.controller;

import geo.track.domain.RegistroEntrada;
import geo.track.dto.registroEntrada.request.PostRegistroEntrada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;
import geo.track.exception.ExceptionBody; // Import necessário
import geo.track.service.RegistroEntradaService;
import io.swagger.v3.oas.annotations.Operation; // Import necessário
import io.swagger.v3.oas.annotations.media.ArraySchema; // Import necessário
import io.swagger.v3.oas.annotations.media.Content; // Import necessário
import io.swagger.v3.oas.annotations.media.Schema; // Import necessário
import io.swagger.v3.oas.annotations.responses.ApiResponse; // Import necessário
import io.swagger.v3.oas.annotations.responses.ApiResponses; // Import necessário
import io.swagger.v3.oas.annotations.tags.Tag; // Import necessário
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entrada")
@RequiredArgsConstructor
@Tag(name = "Registro de Entrada", description = "Endpoints para gerenciar os registros de entrada de veículos") // Adicionado
public class RegistroEntradaController {
    private final RegistroEntradaService registroService;

    @Operation( // Adicionado
            summary = "Cadastrar novo registro de entrada",
            description = "Recebe um objeto e o armazena, retornando o registro criado."
    )
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "201",
                    description = "Registro de entrada cadastrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = RegistroEntrada.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para o registro",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PostMapping
    public ResponseEntity<RegistroEntrada> postRegistro(@RequestBody PostRegistroEntrada registroDTO){ // Corrigido
        RegistroEntrada registro = registroService.postRegistro(registroDTO);
        return ResponseEntity.status(201).body(registro);
    }

    @Operation(summary = "Buscar todos os registros de entrada") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "200",
                    description = "Registros encontrados com sucesso",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RegistroEntrada.class)))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhum registro de entrada encontrado",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<RegistroEntrada>> findRegistro(){
        List<RegistroEntrada> registro = registroService.findRegistro();
        if (registro.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(registro);
    }


    @Operation(summary = "Buscar registro de entrada pelo ID") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "200",
                    description = "Registro encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = RegistroEntrada.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionBody.class))
            )
    })
    @GetMapping("/{idRegistro}")
    public ResponseEntity<RegistroEntrada> findRegistroById(@PathVariable Integer idRegistro){ // Corrigido
        RegistroEntrada registro = registroService.findRegistroById(idRegistro);
        return ResponseEntity.status(200).body(registro);
    }

    @Operation( // Adicionado
            summary = "Atualizar completamente um registro de entrada",
            description = "Recebe um objeto com os novos dados do registro e o identificador e retornando o registro atualizado."
    )
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "200",
                    description = "Registro atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = RegistroEntrada.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para a atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado para atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PutMapping
    public ResponseEntity<RegistroEntrada> putRegistro(@RequestBody RequestPutRegistroEntrada registroDTO){ // Corrigido
        RegistroEntrada registro = registroService.putRegistro(registroDTO);
        return ResponseEntity.status(200).body(registro);
    }

    @Operation(summary = "Remover um registro de entrada") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "204",
                    description = "Registro removido com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado para remoção",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @DeleteMapping("/{idRegistro}") // Corrigido
    public ResponseEntity<Void> deleteRegistro(@PathVariable Integer idRegistro){
        registroService.deleteRegistro(idRegistro);
        return ResponseEntity.status(204).build();
    }

}