package geo.track.domain;

import geo.track.enums.os.StatusVeiculo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class OrdemDeServicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ordem_servico;
    @NotNull
    private Double valor_total;
    private LocalDate dt_saida_prevista;
    private LocalDate dt_saida_efetiva;
    @NotNull
    private StatusVeiculo status;
    private Boolean seguradora;
    private Boolean nf_realizada;
    private Boolean pagt_realizado;
    private Integer fk_entrada;

    public OrdemDeServicos() {
    }

    public OrdemDeServicos(Integer id_ordem_servico, Double valor_total, LocalDate dt_saida_prevista, LocalDate dt_saida_efetiva, StatusVeiculo status, Boolean seguradora, Boolean nf_realizada, Boolean pagt_realizado, Integer fk_entrada) {
        this.id_ordem_servico = id_ordem_servico;
        this.valor_total = valor_total;
        this.dt_saida_prevista = dt_saida_prevista;
        this.dt_saida_efetiva = dt_saida_efetiva;
        this.status = status;
        this.seguradora = seguradora;
        this.nf_realizada = nf_realizada;
        this.pagt_realizado = pagt_realizado;
        this.fk_entrada = fk_entrada;
    }

    public Integer getId_ordem_servico() {
        return id_ordem_servico;
    }

    public void setId_ordem_servico(Integer id_ordem_servico) {
        this.id_ordem_servico = id_ordem_servico;
    }

    public Double getValor_total() {
        return valor_total;
    }

    public void setValor_total(Double valor_total) {
        this.valor_total = valor_total;
    }

    public LocalDate getDt_saida_prevista() {
        return dt_saida_prevista;
    }

    public void setDt_saida_prevista(LocalDate dt_saida_prevista) {
        this.dt_saida_prevista = dt_saida_prevista;
    }

    public LocalDate getDt_saida_efetiva() {
        return dt_saida_efetiva;
    }

    public void setDt_saida_efetiva(LocalDate dt_saida_efetiva) {
        this.dt_saida_efetiva = dt_saida_efetiva;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    public Boolean getSeguradora() {
        return seguradora;
    }

    public void setSeguradora(Boolean seguradora) {
        this.seguradora = seguradora;
    }

    public Boolean getNf_realizada() {
        return nf_realizada;
    }

    public void setNf_realizada(Boolean nf_realizada) {
        this.nf_realizada = nf_realizada;
    }

    public Boolean getPagt_realizado() {
        return pagt_realizado;
    }

    public void setPagt_realizado(Boolean pagt_realizado) {
        this.pagt_realizado = pagt_realizado;
    }

    public Integer getFk_entrada() {
        return fk_entrada;
    }

    public void setFk_entrada(Integer fk_entrada) {
        this.fk_entrada = fk_entrada;
    }
}
