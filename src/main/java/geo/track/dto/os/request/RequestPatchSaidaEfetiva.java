package geo.track.dto.os.request;

import java.time.LocalDate;

public class RequestPatchSaidaEfetiva {
    private Integer idOrdem;
    private Integer fkVeiculo;
    private LocalDate dtSaidaEfeiva;

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

    public LocalDate getDtSaidaEfeiva() {
        return dtSaidaEfeiva;
    }

    public void setDtSaidaEfeiva(LocalDate dtSaidaEfeiva) {
        this.dtSaidaEfeiva = dtSaidaEfeiva;
    }
}
