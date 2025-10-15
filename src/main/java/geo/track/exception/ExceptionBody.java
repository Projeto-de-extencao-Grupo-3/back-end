package geo.track.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Objeto utilizado para enviar as informações referente à exceção")
public class ExceptionBody {
    @Schema(description = "Categoria em que ocorreu a exceção", example = "Oficinas", requiredMode = Schema.RequiredMode.REQUIRED)
    private String domain;

    @Schema(description = "Mensagem referente à exceção", example = "Oficina não encontrada", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mensagem;

    @Schema(description = "Momento em que ocorreu a exceção", example = "2025-10-15T19:36:15.0484801", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime momento;

    @Schema(description = "Qual a exceção que ocorreu", example = "DataNotFoundException", requiredMode = Schema.RequiredMode.REQUIRED)
    private String excesao;

    @Schema(description = "Código HTTP referente a exceção que ocorreu", example = "404", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer codigo;
}
