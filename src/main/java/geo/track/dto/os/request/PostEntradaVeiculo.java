package geo.track.dto.os.request;

import geo.track.enums.os.StatusVeiculo;


public class PostEntradaVeiculo {
    private StatusVeiculo status;
    private Double valorTotal = 0.0;
    private Integer fkEntrada;

    public PostEntradaVeiculo(StatusVeiculo status, Double valorTotal, Integer fkEntrada) {
        this.status = status;
        this.valorTotal = valorTotal;
        this.fkEntrada = fkEntrada;
    }

    public PostEntradaVeiculo() {
    }


    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getFkEntrada() {
        return fkEntrada;
    }

    public void setFkEntrada(Integer fkEntrada) {
        this.fkEntrada = fkEntrada;
    }
}
