package geo.track.dto.produtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchQtdEstoque {
    private Integer id;
    private Integer quantidadeEstoque;
}
