package geo.track.controller;

import geo.track.domain.Enderecos;
import geo.track.dto.enderecos.request.RequestPatchComplemento;
import geo.track.dto.enderecos.request.RequestPatchNumero;
import geo.track.dto.enderecos.request.RequestPostEndereco;
import geo.track.dto.enderecos.request.RequestPutEndereco;
import geo.track.dto.viacep.response.ResponseViacep;
import geo.track.exception.ExceptionBody;
import geo.track.exception.constraint.message.EnderecosExceptionMessages;
import geo.track.service.EnderecosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/enderecos")
@RestController
@RequiredArgsConstructor
@Tag(name = "Endereços", description = "Endpoints utilizados para gerenciar os endereços")
@SecurityRequirement(name = "Bearer")
public class EnderecosController {
    private final EnderecosService enderecosService;

    @Operation(summary = "Buscar endereço pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = Enderecos.class))
            }),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Enderecos> getEnderecoById(@PathVariable Integer id) {
        Enderecos enderecos = enderecosService.findEnderecoById(id);

        if (enderecos == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(enderecos);
    }

    @Operation(summary = "Buscar dados de endereço com o CEP do VIACEP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso na base de dados do VIACEP", content = {@Content(schema = @Schema(implementation = ResponseViacep.class))}),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado na base de dados do VIACEP", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "406", description = EnderecosExceptionMessages.formatacaoCEPException, content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/viacep/{cep}")
    public ResponseEntity<ResponseViacep> findEnderecoByVIACEP(@PathVariable String cep) {
        ResponseViacep responseCep = enderecosService.findEnderecoByVIACEP(cep);

        if (responseCep == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(responseCep);
    }

    @Operation(
            summary = "Cadastrar novo endereço",
            description = "Recebe um objeto e o armazena, retornando o endereço criado e seu devido ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Endereço cadastrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Enderecos.class))}
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = EnderecosExceptionMessages.formatacaoCEPException,
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PostMapping()
    public ResponseEntity<Enderecos> postEndereco(@RequestBody RequestPostEndereco endereco) {
        return ResponseEntity.status(201).body(enderecosService.postEndereco(endereco));
    }

    @Operation(
            summary = "Atualizar o complemento de um endereço",
            description = "Recebe um objeto com o novo complemento e o identificador e retornando o endereço atualizado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Endereço atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Enderecos.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Endereço não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
    })
    @PatchMapping("/complemento")
    public ResponseEntity<Enderecos> patchComplementoEndereco(@RequestBody RequestPatchComplemento enderecoDTO) {
        Enderecos enderecos = enderecosService.patchComplementoEndereco(enderecoDTO);

        return ResponseEntity.status(200).body(enderecos);
    }

    @Operation(
            summary = "Atualizar o número de um endereço",
            description = "Recebe um objeto com o novo número e o identificador e retornando o endereço atualizado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Endereço atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Enderecos.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Endereço não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
    })
    @PatchMapping("/numero")
    public ResponseEntity<Enderecos> patchNumeroEndereco(@RequestBody RequestPatchNumero enderecoDTO) {
        Enderecos enderecos = enderecosService.patchNumeroEndereco(enderecoDTO);

        if (enderecos == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(enderecos);
    }

    @Operation(
            summary = "Atualizar completamente um endereço",
            description = "Recebe um objeto com o novo endereço e o identificador e retornando o endereço atualizado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Endereço atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Enderecos.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Endereço não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(responseCode = "406", description = EnderecosExceptionMessages.formatacaoCEPException, content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping()
    public ResponseEntity<Enderecos> putEndereco(@RequestBody RequestPutEndereco enderecoDTO) {
        Enderecos enderecos = enderecosService.putEndereco(enderecoDTO);

        return ResponseEntity.status(200).body(enderecos);
    }

}
