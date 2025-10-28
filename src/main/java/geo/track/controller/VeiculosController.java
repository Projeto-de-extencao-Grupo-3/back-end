package geo.track.controller;

import geo.track.domain.Veiculos;
import geo.track.dto.veiculos.request.RequestPatchCor;
import geo.track.dto.veiculos.request.RequestPatchPlaca;
import geo.track.exception.ExceptionBody; // Import necessário
import geo.track.service.VeiculosService;
import io.swagger.v3.oas.annotations.Operation; // Import necessário
import io.swagger.v3.oas.annotations.media.ArraySchema; // Import necessário
import io.swagger.v3.oas.annotations.media.Content; // Import necessário
import io.swagger.v3.oas.annotations.media.Schema; // Import necessário
import io.swagger.v3.oas.annotations.responses.ApiResponse; // Import necessário
import io.swagger.v3.oas.annotations.responses.ApiResponses; // Import necessário
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag; // Import necessário
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
@Tag(name = "Veículos", description = "Endpoints utilizados para gerenciar os veículos") // Adicionado
@SecurityRequirement(name = "Bearer")
public class VeiculosController {

    private final VeiculosService service;

    @Operation( // Adicionado
            summary = "Cadastrar novo veículo",
            description = "Recebe um objeto de veículo e o armazena, retornando o veículo criado."
    )
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "201",
                    description = "Veículo cadastrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Veiculos.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para o veículo",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Veículo com esta placa já cadastrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PostMapping
    public ResponseEntity<Veiculos>cadastrar(@Valid @RequestBody Veiculos veiculo){
        Veiculos veicCadastrado = service.cadastrar(veiculo);
        return ResponseEntity.status(201).body(veicCadastrado); // Atenção: talvez devesse retornar veicCadastrado
    }

    @Operation(summary = "Listar todos os veículos") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de veículos encontrada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Veiculos.class)))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhum veículo cadastrado",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<Veiculos>>listar(){
        List<Veiculos>listaVeiculos = service.listar();

        if(listaVeiculos.isEmpty()){
            return ResponseEntity.status(204).body(listaVeiculos);
        }

        return ResponseEntity.status(200).body(listaVeiculos);
    }

    @Operation(summary = "Buscar veículo pelo ID") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Veiculos.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionBody.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Veiculos> findVeiculoById(@PathVariable Integer id){
        Veiculos veic = service.findVeiculoById(id);
        return ResponseEntity.status(200).body(veic);
    }

    @Operation(summary = "Buscar veículo(s) pela placa") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo(s) encontrado(s) com sucesso",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Veiculos.class)))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhum veículo encontrado para esta placa",
                    content = @Content
            )
    })
    @GetMapping("/placa/{placa}")
    public ResponseEntity<List<Veiculos>>findVeiculoByPlaca(@PathVariable String placa){
        List<Veiculos>veic = service.findVeiculoByPlaca(placa);

        if(veic.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(veic);
    }

    @Operation(summary = "Atualizar completamente um veículo") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Veiculos.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para a atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado para atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Veiculos>putVeiculo(@PathVariable Integer id, @RequestBody Veiculos veiculoAtt){
        Veiculos veic = service.putEndereco(id, veiculoAtt);
        return ResponseEntity.status(200).body(veic);
    }

    @Operation(summary = "Atualizar a placa de um veículo") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "200",
                    description = "Placa do veículo atualizada com sucesso",
                    content = {@Content(schema = @Schema(implementation = Veiculos.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos (ex: formato de placa)",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PatchMapping("/placa")
    public ResponseEntity<Veiculos>patchPlaca(@RequestBody RequestPatchPlaca veiculoDTO){
        Veiculos veic = service.patchPlaca(veiculoDTO);
        return ResponseEntity.status(200).body(veic);
    }

    //---
    @Operation(summary = "Atualizar a cor de um veículo") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "200",
                    description = "Cor do veículo atualizada com sucesso",
                    content = {@Content(schema = @Schema(implementation = Veiculos.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos (ex: cor em branco)",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PatchMapping("/cor")
    public ResponseEntity<Veiculos>patchCor(@RequestBody RequestPatchCor veiculoDTO){
        Veiculos veic = service.patchCor(veiculoDTO);
        return ResponseEntity.status(200).body(veic);
    }

    @Operation(summary = "Remover um veículo pelo ID") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "204",
                    description = "Veículo removido com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado para remoção",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteVeiculoById(@PathVariable Integer id){
        service.deleteVeiculoById(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Remover um veículo pela placa") // Adicionado
    @ApiResponses(value = { // Adicionado
            @ApiResponse(
                    responseCode = "204",
                    description = "Veículo removido com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado para remoção",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @DeleteMapping("/placa/{placa}")
    public ResponseEntity<Void>deleteVeiculoByPlaca(@PathVariable String placa){
        service.deleteVeiculoByPlaca(placa);
        return ResponseEntity.status(204).build();
    }

}