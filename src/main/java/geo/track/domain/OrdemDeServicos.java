package geo.track.domain;

import geo.track.enums.os.StatusVeiculo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdemDeServicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da ordem de serviço", example = "101")
    private Integer idOrdemServico;

    @NotNull
    @Schema(description = "Valor total da ordem de serviço", example = "850.00")
    private Double valorTotal;

    @Schema(description = "Data prevista de saída do veículo", example = "2025-11-05")
    private LocalDate dtSaidaPrevista;

    @Schema(description = "Data efetiva de saída do veículo", example = "2025-11-06")
    private LocalDate dtSaidaEfetiva;

    @NotNull
    @Schema(description = "Status atual do veículo", example = "EM_REPARO")
    private StatusVeiculo status;

    @Schema(description = "Indica se o serviço envolve seguradora", example = "true")
    private Boolean seguradora;

    @Schema(description = "Indica se a nota fiscal já foi emitida", example = "false")
    private Boolean nfRealizada;

    @Schema(description = "Indica se o pagamento foi realizado", example = "true")
    private Boolean pagtRealizado;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_entrada")
    @Schema(description = "Registro de entrada relacionado á ordem de serviço.")
    private RegistroEntrada fk_entrada;

    @OneToMany(mappedBy = "fkOrdemServico", cascade = CascadeType.ALL)
    @Schema(description = "Lista de produtos associados à ordem de serviço")
    private List<ItensProdutos> produtos;

    @OneToMany(mappedBy = "fkOrdemServico", cascade = CascadeType.ALL)
    @Schema(description = "Lista de serviços associados à ordem de serviço")
    private List<ItensServicos> servicos;
}
