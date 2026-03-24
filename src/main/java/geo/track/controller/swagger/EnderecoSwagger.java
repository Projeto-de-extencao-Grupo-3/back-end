package geo.track.controller.swagger;

import geo.track.dto.enderecos.request.RequestPatchComplemento;
import geo.track.dto.enderecos.request.RequestPatchNumero;
import geo.track.dto.enderecos.request.RequestPostEndereco;
import geo.track.dto.enderecos.request.RequestPutEndereco;
import geo.track.dto.enderecos.response.EnderecoResponse;
import geo.track.dto.viacep.response.ResponseViacep;
import geo.track.infraestructure.exception.ExceptionBody;
import geo.track.infraestructure.exception.constraint.message.EnderecoExceptionMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Endereços", description = "Endpoints utilizados para gerenciar os endereços")
@SecurityRequirement(name = "Bearer")
public interface EnderecoSwagger {

    @Operation(summary = "Buscar endereço pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<EnderecoResponse> getEnderecoById(@PathVariable Integer id);

    @Operation(summary = "Buscar dados de endereço com o CEP do VIACEP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso na base de dados do VIACEP", content = {@Content(schema = @Schema(implementation = ResponseViacep.class))}),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado na base de dados do VIACEP", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "406", description = EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/viacep/{cep}")
    ResponseEntity<ResponseViacep> findEnderecoByVIACEP(@PathVariable String cep);

    @Operation(
            summary = "Cadastrar novo endereço",
            description = "Recebe um objeto e o armazena, retornando o endereço criado e seu devido ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Endereço cadastrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = EnderecoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA,
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PostMapping()
    ResponseEntity<EnderecoResponse> postEndereco(@RequestBody RequestPostEndereco endereco);

    @Operation(
            summary = "Atualizar o complemento de um endereço",
            description = "Recebe um objeto com o novo complemento e o identificador e retornando o endereço atualizado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Endereço atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = EnderecoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Endereço não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
    })
    @PatchMapping("/complemento")
    ResponseEntity<EnderecoResponse> patchComplementoEndereco(@RequestBody RequestPatchComplemento enderecoDTO);

    @Operation(
            summary = "Atualizar o número de um endereço",
            description = "Recebe um objeto com o novo número e o identificador e retornando o endereço atualizado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Endereço atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = EnderecoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Endereço não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
    })
    @PatchMapping("/numero")
    ResponseEntity<EnderecoResponse> patchNumeroEndereco(@RequestBody RequestPatchNumero enderecoDTO);

    @Operation(
            summary = "Atualizar completamente um endereço",
            description = "Recebe um objeto com o novo endereço e o identificador e retornando o endereço atualizado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Endereço atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = EnderecoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Endereço não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(responseCode = "406", description = EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping()
    ResponseEntity<EnderecoResponse> putEndereco(@RequestBody RequestPutEndereco enderecoDTO);
}
