package geo.track.dto.produtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchPrecoCompra {
    private Integer id;
    private Double precoCompra;
}
