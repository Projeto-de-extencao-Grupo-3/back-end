package geo.track.controller;

import geo.track.domain.Veiculos;
import geo.track.dto.veiculos.request.RequestPatchCor;
import geo.track.dto.veiculos.request.RequestPatchPlaca;
import geo.track.dto.veiculos.response.VeiculoResponse;
import geo.track.exception.ExceptionBody;
import geo.track.mapper.VeiculoMapper;
import geo.track.service.VeiculosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping("/veiculos")
@RequiredArgsConstructor
@Tag(name = "Veículos", description = "Endpoints utilizados para gerenciar os veículos")
@SecurityRequirement(name = "Bearer")
public class VeiculosController {

    private final VeiculosService service;

    @Operation(
            summary = "Cadastrar novo veículo",
            description = "Recebe um objeto de veículo e o armazena, retornando o veículo criado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Veículo cadastrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
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
    public ResponseEntity<VeiculoResponse>cadastrar(@Valid @RequestBody Veiculos veiculo){
        Veiculos veicCadastrado = service.cadastrar(veiculo);
        return ResponseEntity.status(201).body(VeiculoMapper.toResponse(veicCadastrado));
    }

    @Operation(summary = "Listar todos os veículos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de veículos encontrada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VeiculoResponse.class)))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhum veículo cadastrado",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<VeiculoResponse>>listar(){
        List<Veiculos>listaVeiculos = service.listar();

        if(listaVeiculos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(listaVeiculos));
    }

    @Operation(summary = "Buscar veículo pelo ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionBody.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> findVeiculoById(@PathVariable Integer id){
        Veiculos veic = service.findVeiculoById(id);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Operation(summary = "Buscar veículo(s) pela placa")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo(s) encontrado(s) com sucesso",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VeiculoResponse.class)))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhum veículo encontrado para esta placa",
                    content = @Content
            )
    })
    @GetMapping("/placa/{placa}")
    public ResponseEntity<List<VeiculoResponse>>findVeiculoByPlaca(@PathVariable String placa){
        List<Veiculos>veic = service.findVeiculoByPlaca(placa);

        if(veic.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Operation(summary = "Atualizar completamente um veículo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
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
    public ResponseEntity<VeiculoResponse>putVeiculo(@PathVariable Integer id, @RequestBody Veiculos veiculoAtt){
        Veiculos veic = service.putEndereco(id, veiculoAtt);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Operation(summary = "Atualizar a placa de um veículo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Placa do veículo atualizada com sucesso",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
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
    public ResponseEntity<VeiculoResponse>patchPlaca(@RequestBody RequestPatchPlaca veiculoDTO){
        Veiculos veic = service.patchPlaca(veiculoDTO);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Operation(summary = "Atualizar a cor de um veículo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cor do veículo atualizada com sucesso",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
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
    public ResponseEntity<VeiculoResponse>patchCor(@RequestBody RequestPatchCor veiculoDTO){
        Veiculos veic = service.patchCor(veiculoDTO);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Operation(summary = "Remover um veículo pelo ID")
    @ApiResponses(value = {
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

    @Operation(summary = "Remover um veículo pela placa")
    @ApiResponses(value = {
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