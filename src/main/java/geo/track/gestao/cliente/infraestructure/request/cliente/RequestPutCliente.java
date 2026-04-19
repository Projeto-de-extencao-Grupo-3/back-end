package geo.track.gestao.cliente.infraestructure.request.cliente;

import geo.track.gestao.cliente.infraestructure.persistence.entity.TipoCliente;
import geo.track.gestao.cliente.infraestructure.request.contato.RequestPutContato;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPutEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Objeto de requisição para atualizar (PUT) todos os dados de um cliente")
public class RequestPutCliente {

    @NotNull
    @Schema(description = "ID do cliente a ser atualizado", example = "1")
    private Integer idCliente;

    @NotBlank
    @Schema(description = "Nome completo do cliente", example = "João da Silva")
    private String nome;

    @Size(min = 11, max = 14)
    @Schema(description = "Número do CPF ou CNPJ do cliente", example = "12345678901")
    private String cpfCnpj;

    @Schema(description = "Inscrição Estaudl do cliente", example = "11987654321")
    private String inscricaoEstadual;
}