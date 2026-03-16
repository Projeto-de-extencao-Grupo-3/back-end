package geo.track.dto.veiculos.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RequestPutVeiculo {

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotBlank
    private String prefixo;

    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 0)
    private Integer anoModelo;

}