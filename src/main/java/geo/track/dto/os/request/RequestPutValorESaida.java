package geo.track.dto.os.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RequestPutValorESaida {
    @NotNull
    private Integer idOrdem;
    @NotNull
    private Double valorTotal;
    @FutureOrPresent
    private LocalDate saidaPrevista;
}
