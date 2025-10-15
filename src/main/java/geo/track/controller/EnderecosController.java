package geo.track.controller;

import geo.track.domain.Enderecos;
import geo.track.dto.enderecos.request.RequestPatchComplemento;
import geo.track.dto.enderecos.request.RequestPatchNumero;
import geo.track.dto.enderecos.request.RequestPutEndereco;
import geo.track.dto.viacep.response.ResponseViacep;
import geo.track.exception.ExceptionBody;
import geo.track.service.EnderecosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/enderecos")
@RestController
@RequiredArgsConstructor
@Tag(name = "Endereços", description = "Endpoints utilizados para gerenciar os endereços")
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
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado na base de dados do VIACEP", content = @Content(schema = @Schema(implementation = ObjectUtils.Null.class))),
            @ApiResponse(responseCode = "406", description = "Formato de CEP que foi enviado está incorreto", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/viacep/{cep}")
    public ResponseEntity<ResponseViacep> findEnderecoByVIACEP(@PathVariable String cep) {
        ResponseViacep responseCep = enderecosService.findEnderecoByVIACEP(cep);

        if (responseCep == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(responseCep);
    }

    @PostMapping()
    public ResponseEntity<Enderecos> postEndereco(@RequestBody Enderecos endereco) {
        return ResponseEntity.status(201).body(enderecosService.postEndereco(endereco));
    }

    @PatchMapping("/complemento")
    public ResponseEntity<Enderecos> patchComplementoEndereco(@RequestBody RequestPatchComplemento enderecoDTO) {
        Enderecos enderecos = enderecosService.patchComplementoEndereco(enderecoDTO);

        if (enderecos == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(enderecos);
    }

    @PatchMapping("/numero")
    public ResponseEntity<Enderecos> patchNumeroEndereco(@RequestBody RequestPatchNumero enderecoDTO) {
        Enderecos enderecos = enderecosService.patchNumeroEndereco(enderecoDTO);

        if (enderecos == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(enderecos);
    }

    @PutMapping()
    public ResponseEntity<Enderecos> putEndereco(@RequestBody RequestPutEndereco enderecoDTO) {
        Enderecos enderecos = enderecosService.putEndereco(enderecoDTO);

        if (enderecos == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(enderecos);
    }

}
