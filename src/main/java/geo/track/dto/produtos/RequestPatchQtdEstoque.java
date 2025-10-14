package geo.track.dto.produtos;

import lombok.Data;

@Data
public class RequestPatchQtdEstoque {
    private Integer id;
    private Integer quantidadeEstoque;
}
