package geo.track.dto.produtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchPrecoVenda {
    private Integer id;
    private Double precoVenda;
}
