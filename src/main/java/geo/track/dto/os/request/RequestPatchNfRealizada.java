package geo.track.dto.os.request;

public class RequestPatchNfRealizada {
    private Integer idOrdem;
    private Integer fkVeiculo;
    private Boolean nfRealizada;

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

    public Boolean getNfRealizada() {
        return nfRealizada;
    }

    public void setNfRealizada(Boolean nfRealizada) {
        this.nfRealizada = nfRealizada;
    }
}
