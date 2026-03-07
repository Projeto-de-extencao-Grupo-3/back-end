package geo.track.domain;

import geo.track.enums.os.StatusVeiculo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ordem_de_servicos")
public class OrdemDeServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da ordem de serviço", example = "101")
    private Integer idOrdemServico;

//    @NotNull
//    @Schema(description = "Valor total da ordem de serviço", example = "850.00")
//    private Double valorTotal;

    @Schema(description = "Data prevista de saída do veículo", example = "2025-11-05")
    private LocalDate dataSaidaPrevista;

    @Schema(description = "Data efetiva de saída do veículo", example = "2025-11-06")
    private LocalDate dataSaidaEfetiva;

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
    public Oficinas getOficina() {
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

    private OrdemDeServico(Builder builder) {
        this.idOrdemServico = builder.idOrdemServico;
        this.dataSaidaPrevista = builder.dtSaidaPrevista;
        this.dataSaidaEfetiva = builder.dataSaidaEfetiva;
        this.status = builder.status;
        this.seguradora = builder.seguradora;
        this.nfRealizada = builder.nfRealizada;
        this.pagtRealizado = builder.pagtRealizado;
        this.fkEntrada = builder.fkEntrada;
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
        private LocalDate dataSaidaEfetiva;
        @Enumerated(EnumType.STRING)
        private StatusVeiculo status;
        private Boolean seguradora;
        private Boolean nfRealizada;
        private Boolean pagtRealizado;
        private Boolean ativo;
        private RegistroEntrada fkEntrada;
        private List<ItemProduto> produtos;
        private List<ItemServico> servicos;

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

        public Builder dataSaidaEfetiva(LocalDate dataSaidaEfetiva) {
            this.dataSaidaEfetiva = dataSaidaEfetiva;
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

        public Builder fkEntrada(RegistroEntrada fkEntrada) {
            this.fkEntrada = fkEntrada;
            return this;
        }

        public Builder produtos(List<ItemProduto> produtos) {
            this.produtos = produtos;
            return this;
        }

        public Builder servicos(List<ItemServico> servicos) {
            this.servicos = servicos;
            return this;
        }

        public OrdemDeServico build() {
            return new OrdemDeServico(this);
        }
    }
}