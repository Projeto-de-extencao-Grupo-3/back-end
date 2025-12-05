package geo.track.controller;

import geo.track.domain.ItensServicos;
import geo.track.domain.OrdemDeServicos;
import geo.track.domain.Servicos;
import geo.track.dto.itensServicos.ItensServicoResponse;
import geo.track.dto.itensServicos.RequestPostItemServico;
import geo.track.exception.ExceptionBody;
import geo.track.mapper.ItensServicoMapper;
import geo.track.service.ItensServicosService;
import geo.track.service.OrdemDeServicosService;
import geo.track.service.ServicosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itensServico")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@Tag(name = "ItensServicos", description = "Endpoints utilizados para gerenciar ItensServicos.")
public class ItensServicoController {

    private final ItensServicosService REGISTRO_SERVICO_SERVICE;
    private final OrdemDeServicosService ORDEM_SERVICO_SERVICE;
    private final ServicosService SERVICO_SERVICE;

    @Operation(summary = "Cadastrar ItensServico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item de Serviço cadastrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ItensServicoResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    public ResponseEntity<ItensServicoResponse> cadastrar(@RequestBody RequestPostItemServico body) {
        OrdemDeServicos ordemServico = ORDEM_SERVICO_SERVICE.findOrdemById(body.getFkOrdemServico());
        Servicos servico = SERVICO_SERVICE.buscarPorId(body.getFkServico());
        ItensServicos item = ItensServicoMapper.toDomain(body, ordemServico, servico);

        ItensServicos itensServicos = REGISTRO_SERVICO_SERVICE.cadastrar(item);
        return ResponseEntity.status(201).body(ItensServicoMapper.toResponse(itensServicos));
    }

    @Operation(summary = "Buscar todos os Itens Serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens de Serviço encontrados com sucesso", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ItensServicoResponse.class)))
            }),
            @ApiResponse(responseCode = "204", description = "Nenhum item de serviço encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping
    public ResponseEntity<List<ItensServicoResponse>> findAll() {
        List<ItensServicos> itensServicos = REGISTRO_SERVICO_SERVICE.listar();
        if (itensServicos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ItensServicoMapper.toResponse(itensServicos));
    }

    @Operation(summary = "Buscar Itens Serviço por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de Serviço encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ItensServicoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Item de Serviço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItensServicoResponse> findById(@PathVariable Integer id) {
        ItensServicos itensServicos = REGISTRO_SERVICO_SERVICE.findById(id);
        return ResponseEntity.status(200).body(ItensServicoMapper.toResponse(itensServicos));
    }

    @Operation(summary = "Atualizar Itens Serviço por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de Serviço atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ItensServicoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Item de Serviço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ItensServicoResponse> atualizar(@PathVariable Integer id, @RequestBody ItensServicos itens) {
        ItensServicos itensServicos = REGISTRO_SERVICO_SERVICE.atualizar(id, itens);
        return ResponseEntity.status(200).body(ItensServicoMapper.toResponse(itensServicos));
    }

    @Operation(summary = "Deletar Itens Serviço por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item de Serviço deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de Serviço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        REGISTRO_SERVICO_SERVICE.delete(id);
        return ResponseEntity.status(204).build();
    }
}