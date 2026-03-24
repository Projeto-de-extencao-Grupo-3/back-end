package geo.track.infraestructure.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Schema(description = "Objeto utilizado para enviar as informações referente à exceção")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionBody {
    @Schema(description = "Categoria em que ocorreu a exceção", example = "Oficinas", requiredMode = Schema.RequiredMode.REQUIRED)
    private String domain;

    @Schema(description = "Mensagem referente à exceção", example = "Oficina não encontrada", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mensagem;

    @Schema(description = "Campos inválidos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<InvalidField> camposInvalidos;

    @Schema(description = "Momento em que ocorreu a exceção", example = "2025-10-15T19:36:15.0484801", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime momento;

    @Schema(description = "Qual a exceção que ocorreu", example = "DataNotFoundException", requiredMode = Schema.RequiredMode.REQUIRED)
    private String excecao;

    @Schema(description = "Código HTTP referente a exceção que ocorreu", example = "404", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer codigo;



    public ExceptionBody(String domain, String mensagem, LocalDateTime momento, String excecao, Integer codigo) {
        this.domain = domain;
        this.mensagem = mensagem;
        this.camposInvalidos = null;
        this.momento = momento;
        this.excecao = excecao;
        this.codigo = codigo;
    }
}
