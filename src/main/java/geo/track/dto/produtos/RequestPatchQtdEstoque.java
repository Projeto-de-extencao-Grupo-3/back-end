package geo.track.dto.produtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPatchQtdEstoque {
    @NotNull
    private Integer id;
    @NotNull
    private Integer quantidadeEstoque;
}
