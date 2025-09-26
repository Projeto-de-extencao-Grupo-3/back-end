package geo.track.dto.enderecos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestPutEndereco {
    @NotNull
    private Integer id;
    @NotBlank
    @Size(max = 8, min = 8)
    private String cep;
    @NotBlank
    private String logradouro;
    @NotNull
    private Integer numero;
    @NotBlank
    private String complemento;
    @NotBlank
    private String bairro;
    @NotBlank
    private String cidade;
    @NotBlank
    private String estado;

}
