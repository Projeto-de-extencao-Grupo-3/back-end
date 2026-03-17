package geo.track.domain;

import geo.track.enums.os.StatusVeiculo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ordem_de_servicos")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrdemDeServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da ordem de serviço", example = "101")
    private Integer idOrdemServico;

    @Schema(description = "Valor total da ordem de serviço", example = "850.00")
    private Double valorTotal;

    @Schema(description = "Valor total da serviços", example = "850.00")
    private Double valorTotalServicos;

    @Schema(description = "Valor total da produtos", example = "850.00")
    private Double valorTotalProdutos;

    @Schema(description = "Data prevista de saída do veículo", example = "2025-11-05")
    private LocalDate dataSaidaPrevista;

    @Schema(description = "Data efetiva de saída do veículo", example = "2025-11-06")
    private LocalDate dataSaidaEfetiva;

    @Schema(description = "Data da última atualização da ordem de serviço", example = "2025-11-06")
    private LocalDate dataAtualizacao;

    @Enumerated(EnumType.STRING)
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
    private RegistroEntrada fkEntrada;

    @OneToMany(mappedBy = "fkOrdemServico")
    @Schema(description = "Lista de produtos associados à ordem de serviço")
    private List<ItemProduto> produtos;

    @Schema(description = "Indica se o serviço está ativo", example = "true")
    private Boolean ativo;

    @OneToMany(mappedBy = "fkOrdemServico")
    @Schema(description = "Lista de serviços associados à ordem de serviço")
    private List<ItemServico> servicos;

    @Transient
    public Oficina getOficina() {
        if (fkEntrada != null && fkEntrada.getFkVeiculo() != null && fkEntrada.getFkVeiculo().getFkCliente() != null) {
            return fkEntrada.getFkVeiculo().getFkCliente().getFkOficina();
        }
        return null;
    }

    public OrdemDeServico(Integer idOrdemServico, Double valorTotal, LocalDate dataSaidaPrevista, LocalDate dataSaidaEfetiva, StatusVeiculo status, Boolean seguradora, Boolean nfRealizada, Boolean pagtRealizado, RegistroEntrada fkEntrada, List<ItemProduto> produtos, List<ItemServico> servicos) {
        this.idOrdemServico = idOrdemServico;
        this.dataSaidaPrevista = dataSaidaPrevista;
        this.dataSaidaEfetiva = dataSaidaEfetiva;
        this.status = status;
        this.seguradora = seguradora;
        this.nfRealizada = nfRealizada;
        this.pagtRealizado = pagtRealizado;
        this.fkEntrada = fkEntrada;
        this.produtos = produtos;
        this.servicos = servicos;
    }
}