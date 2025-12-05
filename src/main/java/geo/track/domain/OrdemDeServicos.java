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
//@AllArgsConstructor -- FOI GERADO NA MÃO TA AI EMBAIXO
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
    private RegistroEntrada fk_entrada;

    @OneToMany(mappedBy = "fkOrdemServico")
    @Schema(description = "Lista de produtos associados à ordem de serviço")
    private List<ItensProdutos> produtos;

    @OneToMany(mappedBy = "fkOrdemServico")
    @Schema(description = "Lista de serviços associados à ordem de serviço")
    private List<ItensServicos> servicos;

    public OrdemDeServicos(Integer idOrdemServico, Double valorTotal, LocalDate dtSaidaPrevista, LocalDate dtSaidaEfetiva, StatusVeiculo status, Boolean seguradora, Boolean nfRealizada, Boolean pagtRealizado, RegistroEntrada fk_entrada, List<ItensProdutos> produtos, List<ItensServicos> servicos) {
        this.idOrdemServico = idOrdemServico;
        this.valorTotal = valorTotal;
        this.dtSaidaPrevista = dtSaidaPrevista;
        this.dtSaidaEfetiva = dtSaidaEfetiva;
        this.status = status;
        this.seguradora = seguradora;
        this.nfRealizada = nfRealizada;
        this.pagtRealizado = pagtRealizado;
        this.fk_entrada = fk_entrada;
        this.produtos = produtos;
        this.servicos = servicos;
    }

    private OrdemDeServicos(Builder builder) {
        this.idOrdemServico = builder.idOrdemServico;
        this.valorTotal = builder.valorTotal;
        this.dtSaidaPrevista = builder.dtSaidaPrevista;
        this.dtSaidaEfetiva = builder.dtSaidaEfetiva;
        this.status = builder.status;
        this.seguradora = builder.seguradora;
        this.nfRealizada = builder.nfRealizada;
        this.pagtRealizado = builder.pagtRealizado;
        this.fk_entrada = builder.fk_entrada;
        this.produtos = builder.produtos;
        this.servicos = builder.servicos;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer idOrdemServico;
        private Double valorTotal;
        private LocalDate dtSaidaPrevista;
        private LocalDate dtSaidaEfetiva;
        private StatusVeiculo status;
        private Boolean seguradora;
        private Boolean nfRealizada;
        private Boolean pagtRealizado;
        private Boolean ativo;
        private RegistroEntrada fk_entrada;
        private List<ItensProdutos> produtos;
        private List<ItensServicos> servicos;

        public Builder idOrdemServico(Integer idOrdemServico) {
            this.idOrdemServico = idOrdemServico;
            return this;
        }

        public Builder valorTotal(Double valorTotal) {
            this.valorTotal = valorTotal;
            return this;
        }

        public Builder dtSaidaPrevista(LocalDate dtSaidaPrevista) {
            this.dtSaidaPrevista = dtSaidaPrevista;
            return this;
        }

        public Builder dtSaidaEfetiva(LocalDate dtSaidaEfetiva) {
            this.dtSaidaEfetiva = dtSaidaEfetiva;
            return this;
        }

        public Builder status(StatusVeiculo status) {
            this.status = status;
            return this;
        }

        public Builder seguradora(Boolean seguradora) {
            this.seguradora = seguradora;
            return this;
        }

        public Builder nfRealizada(Boolean nfRealizada) {
            this.nfRealizada = nfRealizada;
            return this;
        }

        public Builder pagtRealizado(Boolean pagtRealizado) {
            this.pagtRealizado = pagtRealizado;
            return this;
        }

        public Builder ativo(Boolean ativo) {
            this.ativo = ativo;
            return this;
        }

        public Builder fk_entrada(RegistroEntrada fk_entrada) {
            this.fk_entrada = fk_entrada;
            return this;
        }

        public Builder produtos(List<ItensProdutos> produtos) {
            this.produtos = produtos;
            return this;
        }

        public Builder servicos(List<ItensServicos> servicos) {
            this.servicos = servicos;
            return this;
        }

        public OrdemDeServicos build() {
            return new OrdemDeServicos(this);
        }
    }
}