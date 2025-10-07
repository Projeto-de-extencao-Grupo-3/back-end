package geo.track.dto.produtos;

public class RequestPatchPrecoCompra {
    private Integer id;
    private Double precoCompra;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(Double precoCompra) {
        this.precoCompra = precoCompra;
    }
}
