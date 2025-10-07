package geo.track.dto.os.request;

public class RequestPatchSeguradora {
    private Integer idOrdem;
    private Integer fkVeiculo;
    private Boolean seguradora;

    public Integer getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(Integer idOrdem) {
        this.idOrdem = idOrdem;
    }

    public Integer getFkVeiculo() {
        return fkVeiculo;
    }

    public void setFkVeiculo(Integer fkVeiculo) {
        this.fkVeiculo = fkVeiculo;
    }

    public Boolean getSeguradora() {
        return seguradora;
    }

    public void setSeguradora(Boolean seguradora) {
        this.seguradora = seguradora;
    }
}
