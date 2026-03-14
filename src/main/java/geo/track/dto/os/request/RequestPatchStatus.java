package geo.track.dto.os.request;

import geo.track.enums.os.StatusVeiculo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class RequestPatchStatus {
    @NotNull
    private Integer idOrdem;
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusVeiculo status;
}
