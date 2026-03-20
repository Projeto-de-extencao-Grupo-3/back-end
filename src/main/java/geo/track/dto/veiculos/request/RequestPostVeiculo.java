package geo.track.dto.veiculos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestPostVeiculo {

    @NotBlank
    @Size(min = 7, max = 7)
    private String placa;

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

    @NotNull
    private Integer idCliente;
}