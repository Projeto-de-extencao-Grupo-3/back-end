package geo.track.domain;

import jakarta.persistence.*;

@Entity
public class Servicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idServico;
    private String tipoServico;
    private String tituloServico;
    private Integer tempoBase;
    private boolean ativo;

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getTituloServico() {
        return tituloServico;
    }

    public void setTituloServico(String tituloServico) {
        this.tituloServico = tituloServico;
    }

    public Integer getTempoBase() {
        return tempoBase;
    }

    public void setTempoBase(Integer tempoBase) {
        this.tempoBase = tempoBase;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
