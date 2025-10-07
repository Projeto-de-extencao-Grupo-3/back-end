package geo.track.dto.os.request;

public class RequestPatchPagtoRealizado {
    private Integer idOrdem;
    private Integer fkVeiculo;
    private Boolean pagtoRealizado;

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

    public Boolean getPagtoRealizado() {
        return pagtoRealizado;
    }

    public void setPagtoRealizado(Boolean pagtoRealizado) {
        this.pagtoRealizado = pagtoRealizado;
    }
}
